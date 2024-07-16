package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ProjectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.entity.DepartmentProject;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toProject(ProjectRequest request);

    @Mapping(source = "departmentProjects", target = "departments", qualifiedByName = "mapDepartments")
    ProjectResponse toProjectResponse(Project project);

    @Named("mapDepartments")
    static List<Department> mapDepartments(List<DepartmentProject> departmentProjects) {
        return departmentProjects.stream()
                .map(DepartmentProject::getDepartment)
                .toList();
    }

    @Mapping(target = "folders", ignore = true)
    @Mapping(target = "departmentProjects", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    void updateProject(@MappingTarget Project project, ProjectUpdateRequest request);
}
