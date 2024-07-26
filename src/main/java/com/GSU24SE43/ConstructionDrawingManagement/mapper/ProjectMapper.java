package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ContractorResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ProjectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toProject(ProjectRequest request);

    @Mapping(source = "departmentProjects", target = "departments", qualifiedByName = "mapDepartments")
    @Mapping(source = "projectContractors", target = "contractors", qualifiedByName = "mapContractors")
    ProjectResponse toProjectResponse(Project project);

    @Named("mapDepartments")
    static List<Department> mapDepartments(List<DepartmentProject> departmentProjects) {
        return departmentProjects.stream()
                .map(DepartmentProject::getDepartment)
                .toList();
    }

    @Named("mapContractors")
    static List<Contractor> mapContractors(List<ProjectContractor> projectContractors) {
        return projectContractors.stream()
                .map(ProjectContractor::getContractor)
                .toList();
    }
    @Mapping(target = "folders", ignore = true)
    @Mapping(target = "departmentProjects", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    void updateProject(@MappingTarget Project project, ProjectUpdateRequest request);
}
