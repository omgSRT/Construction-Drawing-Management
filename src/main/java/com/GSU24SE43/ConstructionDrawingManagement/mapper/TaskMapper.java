package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.TaskChildCreateByHeadRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.TaskChildCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.TaskParentCreateByHeadRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.TaskParentCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.TaskChildCreateByHeadResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.TaskChildCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.TaskParentCreateByHeadResponse;
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
    Task toTaskByAdmin(TaskChildCreateRequest request);
    @Mapping(source = "department.departmentId", target = "departmentId")
    @Mapping(source = "project.id", target = "projectId")
    TaskParentCreateResponse toTaskParentCreateResponse(Task task);

    @Mapping(source = "parentTask.id", target = "parentTaskId")
    @Mapping(source = "project.id", target = "projectId")
    TaskChildCreateResponse toCreateResponse(Task task);

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "parentTask", ignore = true)
    Task toTaskByHead(TaskParentCreateByHeadRequest request);

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "department.departmentId", target = "departmentId")
    TaskParentCreateByHeadResponse toTaskParentCreateByHeadResponse(Task task);

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "parentTask", ignore = true)
    Task toTaskByHead_2(TaskChildCreateByHeadRequest request);

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "department.departmentId", target = "departmentId")
    TaskChildCreateByHeadResponse toTaskChildCreateByHeadResponse(Task task);
}
