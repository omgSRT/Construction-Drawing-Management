package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffCreateResponse2;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import com.GSU24SE43.ConstructionDrawingManagement.enums.Role;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.StaffMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.AccountRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.DepartmentRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.StaffRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public StaffCreateResponse2 create(Account account,StaffCreateRequest request){
        UUID accountID = request.getAccountId();
        UUID departmentID = request.getDepartmentId();
        boolean checkAccountId = accountRepository.existsByAccountId(accountID);
        boolean checkDepartmentId = departmentRepository.existsByDepartmentId(departmentID);
        if(!checkAccountId) throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST);
        if (!checkDepartmentId) throw new AppException(ErrorCode.DEPARTMENT_NOT_FOUND);
        String role_account = account.getRoleName();
        boolean checkEmail = staffRepository.existsByEmail(request.getEmail());
        if (checkEmail) throw new AppException(ErrorCode.EMAIL_IS_EXISTED);

        Staff staff = new Staff();
        staff.setAccount(account);
        staff.setFullName(request.getFullName());
        staff.setDegree(request.getDegree());
        staff.setEmail(request.getEmail());
        staff.setAddress(request.getAddress());
        staff.setPhone(request.getPhone());
        staff.setCertificate(request.getCertificate());

        if(role_account.equals(Role.HEAD.name())){
            staff.setSupervisor(true);
        }

        return mapper.toStaffCreateResponse2(staffRepository.save(staff));
    }

    public StaffUpdateResponse update(UUID id, StaffUpdateRequest request){
        Staff staff = staffRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.STAFF_IS_EXISTED)
        );
        UUID deparmentId = request.getDepartmentId();
        boolean checkDeparmentId = departmentRepository.existsByDepartmentId(deparmentId);
        if (!checkDeparmentId) throw new AppException(ErrorCode.DEPARTMENT_NOT_FOUND);
        mapper.toStaffUpdateResponse(staff ,request);
        staffRepository.save(staff);
        return mapper.toStaffUpdateResponse(staff);
    }

    public List<Staff> getAll(){
//        try {
//            return paginationUtils.convertListToPage(page,perPage, staffRepository.findAll());
//        }catch (Exception e){
//            throw new RuntimeException("lagggg");
//        }
        return staffRepository.findAll();

    }

    public List<Staff> searchStaff(String fullname){
        return staffRepository.findByFullNameContainingIgnoreCase(fullname);
    }

    public void deleteStaff(UUID id){
        staffRepository.deleteById(id);
    }


}
