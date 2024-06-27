package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ProjectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toProject(ProjectRequest request);

    ProjectResponse toProjectResponse(Project project);

    @Mapping(target = "folders", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "staff", ignore = true)
    void updateProject(@MappingTarget Project project, ProjectUpdateRequest request);
}
