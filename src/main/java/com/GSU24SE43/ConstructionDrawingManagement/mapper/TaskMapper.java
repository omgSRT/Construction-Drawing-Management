package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.*;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.*;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "project", ignore = true)
    Task toTask(TaskParentCreateRequest request);

    @Mapping(target = "department", ignore = true)
    @Mapping(target = "project", ignore = true)
    Task toTaskByProjectId(TaskParentCreateRequestByProjectId request);
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "parentTask", ignore = true)
    Task toTaskByAdmin(TaskChildCreateRequest request);

    @Mapping(target = "project", ignore = true)
//    @Mapping(target = "department", ignore = true)
    @Mapping(target = "parentTask", ignore = true)
    Task toTaskByAdmin_V2(TaskChildCreateRequest_V2 request);
    @Mapping(source = "project.id", target = "projectId")
    TaskParentCreateResponse toTaskParentCreateResponse(Task task);

    @Mapping(source = "parentTask.id", target = "parentTaskId")
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "department.departmentId", target = "departmentId")
    TaskChildCreateResponse toCreateResponse(Task task);

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "parentTask", ignore = true)
    Task toTaskByHead(TaskParentCreateByHeadRequest request);

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "parentTask", ignore = true)
    Task toTaskProjectIdByHead(TaskParentCreateByProjectIdByHeadRequest request);
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "department.departmentId", target = "departmentId")
    TaskParentCreateByHeadResponse toTaskParentCreateByHeadResponse(Task task);

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "parentTask", ignore = true)
    Task toTaskByHead_2(TaskChildCreateByHeadRequest request);


    Task toTaskByHead_3(TaskChildCreateByHead_V2Request request);

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "department.departmentId", target = "departmentId")
    TaskChildCreateByHeadResponse toTaskChildCreateByHeadResponse(Task task);

    @Mapping(source = "project.id", target = "projectId")
    TaskParentUpdateByAdminResponse toTaskParentUpdateByAdminResponse(Task task);

    @Mapping(source = "project.id",target = "projectId")
    @Mapping(source = "department.departmentId", target = "departmentId")
    TaskChildUpdateByAdminResponse toTaskChildUpdateByAdminResponse(Task task);

    @Mapping(source = "parentTask.id", target = "parentTaskId")
    @Mapping(source = "department.departmentId", target = "departmentId")
    @Mapping(source = "project.id", target = "projectId")
    TaskResponse toTaskResponse(Task task);

    @Mapping(source = "parentTask.id", target = "parentTaskId")
    @Mapping(source = "department.departmentId", target = "departmentId")
    @Mapping(source = "project.id", target = "projectId")
    TaskResponseDesigner toResponseDesigner(Task task);
//
//    List<TaskResponse> toTaskResponseList(List<Task> tasks);
}
