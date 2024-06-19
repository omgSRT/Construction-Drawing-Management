package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DepartmentRequest;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    Department toDepartment(DepartmentRequest request);

    @Mapping(target = "staffList", ignore = true)
    @Mapping(target = "taskList", ignore = true)
    @Mapping(target = "projectList", ignore = true)
    void updateDepartment(@MappingTarget Department department, DepartmentRequest request);
}
