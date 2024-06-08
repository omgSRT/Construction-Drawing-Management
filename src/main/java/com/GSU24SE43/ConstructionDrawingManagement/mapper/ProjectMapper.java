package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ProjectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toProject(ProjectRequest request);

    ProjectResponse toProjectResponse(Project project);

    @Mapping(target = "subfolders", ignore = true)
    @Mapping(target = "folder", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "account", ignore = true)
    void updateProject(@MappingTarget Project project, ProjectRequest projectRequest);
}
