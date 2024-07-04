package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.AccessUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Log;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LogMapper {
//    AccessMapper INSTANCE = Mappers.getMapper(AccessMapper.class);

    @Mapping(source = "detailTask.detailTaskId", target = "detailTaskId")
    @Mapping(source = "version.id", target = "versionId")
    AccessCreateResponse toAccessCreateResponse(Log log);

    void updateAccess(@MappingTarget Log log, AccessUpdateRequest request);

//    @Mapping(source = "staff.staffId", target = "staffId")
//    @Mapping(source = "permission", target = "permissionId")
    @Mapping(source = "version.id", target = "versionId")
    @Mapping(source = "detailTask.detailTaskId", target = "detailTaskId")
    AccessUpdateResponse toAccessUpdateResponse(Log log);

//    @Mapping(source = "permission.id", target = "permissionId")
//    @Mapping(source = "staff.staffId", target = "staffId")
    @Mapping(source = "version.id", target = "versionId")
    @Mapping(source = "detailTask.detailTaskId", target = "detailTaskId")
    AccessResponse toAccessResponse(Log log);


    List<AccessResponse> toAccessResponseList(List<Log> logs);


}
