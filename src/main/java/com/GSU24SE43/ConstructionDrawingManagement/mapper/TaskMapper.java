package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.TaskCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.TaskParentCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.TaskCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.TaskParentCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "project", ignore = true)
    Task toTask(TaskParentCreateRequest request);
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "parentTask", ignore = true)
    Task toTask_2(TaskCreateRequest request);
    @Mapping(source = "department.departmentId", target = "departmentId")
    @Mapping(source = "project.id", target = "projectId")
    TaskParentCreateResponse toTaskParentCreateResponse(Task task);

    @Mapping(source = "parentTask.id", target = "parentTaskId")
    TaskCreateResponse toCreateResponse(Task task);


}
