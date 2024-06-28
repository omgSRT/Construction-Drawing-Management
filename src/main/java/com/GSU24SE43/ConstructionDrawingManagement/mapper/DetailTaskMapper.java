package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DetailTaskUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DetailTaskCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DetailTaskResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DetailTaskUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.DetailTask;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DetailTaskMapper {

    @Mapping(source = "staff.staffId", target = "staffId")
    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "permissions", target = "permissionList")
    DetailTaskCreateResponse toCreateResponse(DetailTask detailTask);

    @Mapping(source = "staff.staffId", target = "staffId")
    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "permissions", target = "permissionList")
    DetailTaskUpdateResponse toDetailTaskUpdateResponse(DetailTask task);

    @Mapping(source = "staff.staffId", target = "staffId")
    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "permissions", target = "permissionList")
    DetailTaskResponse toDetailTaskResponse(DetailTask detailTask);
}
