package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffUpdateByStaffRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffCreateResponse2;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffListResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffUpdateByStaffResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import com.GSU24SE43.ConstructionDrawingManagement.enums.Role;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.StaffMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.AccountRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.DepartmentRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.StaffRepository;

import java.util.ArrayList;
import java.util.Collections;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class StaffService {
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    StaffMapper mapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    final PaginationUtils paginationUtils = new PaginationUtils();

    @PreAuthorize("hasRole('ADMIN')")
    public StaffCreateResponse2 create(Account account, StaffCreateRequest request) {
        UUID accountID = request.getAccountId();
        UUID departmentID = request.getDepartmentId();
        boolean checkAccountId = accountRepository.existsByAccountId(accountID);
        boolean checkDepartmentId = departmentRepository.existsByDepartmentId(departmentID);
        if (!checkAccountId) throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST);
        if (!checkDepartmentId) throw new AppException(ErrorCode.DEPARTMENT_NOT_FOUND);
        String roleName = account.getRoleName();
        boolean checkEmail = staffRepository.existsByEmail(request.getEmail());
        if (checkEmail) throw new AppException(ErrorCode.EMAIL_IS_EXISTED);
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        Staff staff = new Staff();
        staff.setAccount(account);
        staff.setFullName(request.getFullName());
        staff.setDegree(request.getDegree());
        staff.setEmail(request.getEmail());
        staff.setAddress(request.getAddress());
        staff.setPhone(request.getPhone());
        staff.setCertificate(request.getCertificate());
        staff.setDepartment(department);

        if (roleName.equals(Role.HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT.name())
                || roleName.equals(Role.HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT.name())
                || roleName.equals(Role.HEAD_OF_MvE_DESIGN_DEPARTMENT.name())
                || roleName.equals(Role.HEAD_OF_INTERIOR_DESIGN_DEPARTMENT.name())) {
            staff.setSupervisor(true);
        }
        return mapper.toStaffCreateResponse2(staffRepository.save(staff));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT','HEAD_OF_MvE_DESIGN_DEPARTMENT','HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public StaffUpdateResponse update(UUID id, StaffUpdateRequest request) {
        Staff staff = staffRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.STAFF_IS_EXISTED)
        );
        boolean checkDepartmentId = departmentRepository.existsByDepartmentId(request.getDepartmentId());
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        if (!checkDepartmentId) throw new AppException(ErrorCode.DEPARTMENT_NOT_FOUND);
        mapper.updateStaff(staff, request);
        staff.setDepartment(department);
        staffRepository.save(staff);
        return mapper.toStaffUpdateResponse(staff);
    }
    @PreAuthorize("hasAuthority('DESIGNER')")
    public StaffUpdateByStaffResponse updateByStaff(UUID id, StaffUpdateByStaffRequest request){
        Staff staff = staffRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.STAFF_IS_EXISTED)
        );
        mapper.updateByStaff(staff, request);
        staffRepository.save(staff);
        return mapper.toStaffUpdateByStaffResponse(staff);
    }
    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT','HEAD_OF_MvE_DESIGN_DEPARTMENT','HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<Staff> getAll() {
        return staffRepository.findAll();

    }
    // thiếu search staff theo từng deparment
//*********************
    //phân trang
    @PreAuthorize("hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT','HEAD_OF_MvE_DESIGN_DEPARTMENT','HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<StaffListResponse> getAllListStaffByHead() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Account account = accountRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Staff staff = account.getStaff();
        Department department = departmentRepository.findById(staff.getDepartment().getDepartmentId()).orElseThrow(
                () -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        if (staff.isSupervisor()) return department.getStaffList().stream().map(mapper::toStaffList).toList();
        return Collections.emptyList();
    }
    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT','HEAD_OF_MvE_DESIGN_DEPARTMENT','HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<Staff> searchStaff(String fullname) {
        return staffRepository.findByFullNameContainingIgnoreCase(fullname);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStaff(UUID id) {
        staffRepository.deleteById(id);
    }


}
