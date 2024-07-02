package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DepartmentRequest;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.DepartmentMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.DepartmentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class DepartmentService {
    final DepartmentRepository departmentRepository;
    final DepartmentMapper departmentMapper;
    final PaginationUtils paginationUtils = new PaginationUtils();

    @PreAuthorize("hasRole('ADMIN')")
    public Department createDepartment(DepartmentRequest request){
        if(departmentRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }

        Department newDepartment = departmentMapper.toDepartment(request);

        return departmentRepository.save(newDepartment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Department> getAllDepartment(int page, int perPage){
        try {
            return paginationUtils.convertListToPage(page, perPage, departmentRepository.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDepartmentById(UUID id){
        var department = departmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        departmentRepository.delete(department);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Department findDepartmentById(UUID id){
        return departmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Department updateDepartmentById(UUID id, DepartmentRequest request){
        var department = departmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        departmentMapper.updateDepartment(department, request);

        return departmentRepository.save(department);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Department> findDepartmentByNameContaining(String name, int page, int perPage){
        try {
            return paginationUtils.convertListToPage(page, perPage, departmentRepository.findByNameContaining(name));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
