package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.*;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.*;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import com.GSU24SE43.ConstructionDrawingManagement.enums.AccountStatus;
import com.GSU24SE43.ConstructionDrawingManagement.enums.Role;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.AccountMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.AccountRepository;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.repository.DepartmentRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.StaffRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    AccountRepository repository;
    AccountMapper accountMapper;
    PasswordEncoder passwordEncoder;
    StaffRepository staffRepository;
    DepartmentRepository departmentRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public AccountCreateResponse accountCreateResponse(AccountCreateRequest request) {
        boolean checkAccountName = repository.existsByUsername(request.getUsername());
        if (checkAccountName) {
            throw new AppException(ErrorCode.ACCOUNT_ARE_EXISTED);
        }
        Account account = accountMapper.toAccount(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRoleName(Role.DESIGNER.name());
        account.setCreatedDate(new Date());
        account.setAccountStatus(AccountStatus.ACTIVE.name());

        account = repository.save(account);

        return accountMapper.toCreateResponse(account);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT','HEAD_OF_MvE_DESIGN_DEPARTMENT','HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public AccountUpdateResponse accountUpdateResponse(UUID accountId, AccountUpdateRequest request) {
        Account account = repository.findById(accountId).orElseThrow(()
                -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));
        if (repository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USERNAME_IS_EXISTED);
        accountMapper.updateAccount(account, request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        repository.save(account);
        return accountMapper.toAccountUpdateResponse(account);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public AccountUpdateStatusResponse updateStatus(UUID id, AccountUpdateStatusRequest request) {
        Account account = repository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));
        String status = request.getAccountStatus();
        if (!status.equals(AccountStatus.ACTIVE.name())
                && (!status.equals(AccountStatus.INACTIVE.name())
                && (!status.equals(AccountStatus.HIDDEN.name())
        ))) {
            throw new AppException(ErrorCode.UNDEFINED_STATUS_ACCOUNT);
        }
        accountMapper.toAccountUpdateStatusResponse(account, request);
        repository.save(account);
        return accountMapper.toAccountUpdateStatusResponse(account);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public AccountUpdateResponse updateRole(UUID id, AccountUpdateRoleRequest request) {
        Account account = repository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));
        String roleName = request.getRoleName();
        if (!roleName.equalsIgnoreCase(Role.DESIGNER.name())
                && !roleName.equalsIgnoreCase(Role.HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT.name())
                && !roleName.equalsIgnoreCase(Role.HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT.name())
                && !roleName.equalsIgnoreCase(Role.HEAD_OF_MvE_DESIGN_DEPARTMENT.name())
                && !roleName.equalsIgnoreCase(Role.HEAD_OF_INTERIOR_DESIGN_DEPARTMENT.name())
                && !roleName.equalsIgnoreCase(Role.ADMIN.name())
                && !roleName.equalsIgnoreCase(Role.COMMANDER.name())
        ) {
            throw new AppException(ErrorCode.ROLE_IS_NOT_DEFINED);
        }
        //check 1 phòng ko có 2 head
        if (roleName.equalsIgnoreCase(Role.HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT.toString())
                || roleName.equalsIgnoreCase(Role.HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT.toString())
                || roleName.equalsIgnoreCase(Role.HEAD_OF_MvE_DESIGN_DEPARTMENT.toString())
                || roleName.equalsIgnoreCase(Role.HEAD_OF_INTERIOR_DESIGN_DEPARTMENT.toString())) {
            if (checkHead(id)) throw new AppException(ErrorCode.ROOM_HAD_HEAD);
        }
        //check ko trùng head
        checkDuplicateHead(roleName);
        account.setRoleName(request.getRoleName());
        Staff staff = account.getStaff();
        if (roleName.equals(Role.HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT.name())
                || roleName.equals(Role.HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT.name())
                || roleName.equals(Role.HEAD_OF_INTERIOR_DESIGN_DEPARTMENT.name())
                || roleName.equals(Role.HEAD_OF_MvE_DESIGN_DEPARTMENT.name())) {
            staff.setSupervisor(true);
        } else staff.setSupervisor(false);
        repository.save(account);
        return accountMapper.toAccountUpdateResponse(account);
    }

    //check 1 phòng ko có 2 head
    private void checkDuplicateHead(String role) {
        if (role.equals(Role.COMMANDER.name())
                || role.equals(Role.DESIGNER.name())
                || role.equals(Role.ADMIN.name())) {
            return;
        }
        List<Department> departments = departmentRepository.findAll();
        departments.forEach(department -> department.getStaffList().forEach(staff -> {
            Account account = repository.findByStaff_StaffId(staff.getStaffId());
            if (account != null && account.getRoleName().equals(role)) {
                throw new AppException(ErrorCode.DUPLICATE_HEAD);
            }
        }));
//        departments.forEach(department -> {
//            department.getStaffList().forEach(staff -> {
//                Account account = repository.findByStaff_StaffId(staff.getStaffId());
//                if (account != null && account.getRoleName().equals(role)) {
//                    throw new AppException(ErrorCode.DUPLICATE_HEAD);
//                }
//            });
//        });
    }

    private Account checkAccount(UUID id) {
        return repository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));
    }

    private Staff checkStaff(UUID id) {
        return staffRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.STAFF_NOT_FOUND)
        );
    }

    private Department checkDepartment(UUID id) {
        return departmentRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
    }

    private boolean checkHead(UUID accountId) {
//        Account account = repository.findById(accountId).orElseThrow(()
//                -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));
//        Staff staff1 = staffRepository.findById(account.getStaff().getStaffId()).orElseThrow(
//                () -> new AppException(ErrorCode.STAFF_NOT_FOUND)
//        );
//        Department department = departmentRepository.findById(staff1.getDepartment().getDepartmentId()).orElseThrow(
//                () -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        Account account = checkAccount(accountId);
        Staff staff1 = checkStaff(account.getStaff().getStaffId());
        Department department = checkDepartment(staff1.getDepartment().getDepartmentId());
        return department.getStaffList().stream().anyMatch(staff -> Objects.equals(staff.isSupervisor(), true));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountResponse> getAllAccount() {
        return repository.findAll().stream().map(accountMapper::toAccountResponse).toList();
    }

    public AccountResponse getAccountInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Account account = repository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST)
        );
        return accountMapper.toAccountResponse(account);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT','HEAD_OF_MvE_DESIGN_DEPARTMENT','HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public Account getAccountByUUID(UUID accountId) {
        return repository.findByAccountId(accountId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT','HEAD_OF_MvE_DESIGN_DEPARTMENT','HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<Account> searchAccount(String username) {
        return repository.findByUsernameContainingIgnoreCase(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAccount(UUID id) {
        checkAccount(id);
        repository.deleteById(id);
    }


}
