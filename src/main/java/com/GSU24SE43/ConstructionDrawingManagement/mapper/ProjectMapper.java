package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ProjectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toProject(ProjectRequest request);

    @Mapping(source = "departmentProjects", target = "departments", qualifiedByName = "mapDepartments")
    @Mapping(source = "projectCustomers", target = "customers", qualifiedByName = "mapContractors")
    @Mapping(source = "landPurpose.name", target = "landPurposeName")
    @Mapping(source = "landPurpose.description", target = "landPurposeDescription")
    ProjectResponse toProjectResponse(Project project);

    @Named("mapDepartments")
    static List<Department> mapDepartments(List<DepartmentProject> departmentProjects) {
        return departmentProjects.stream()
                .map(DepartmentProject::getDepartment)
                .toList();
    }

    @Named("mapContractors")
    static List<Customer> mapContractors(List<ProjectCustomer> projectCustomers) {
        return projectCustomers.stream()
                .map(ProjectCustomer::getCustomer)
                .toList();
    }
    @Mapping(target = "folders", ignore = true)
    @Mapping(target = "departmentProjects", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    void updateProject(@MappingTarget Project project, ProjectUpdateRequest request);
}
