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
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class DepartmentService {
    final DepartmentRepository departmentRepository;
    final DepartmentMapper departmentMapper;
    final PaginationUtils paginationUtils = new PaginationUtils();

    public Department createDepartment(DepartmentRequest request){
        if(departmentRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }

        Department newDepartment = departmentMapper.toDepartment(request);

        return departmentRepository.save(newDepartment);
    }

    public List<Department> getAllDepartment(int page, int perPage){
        try {
            return paginationUtils.convertListToPage(page, perPage, departmentRepository.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
