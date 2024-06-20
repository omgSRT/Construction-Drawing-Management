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
public interface AccessMapper {
//    AccessMapper INSTANCE = Mappers.getMapper(AccessMapper.class);

    AccessCreateResponse toAccessCreateResponse(Log log);

    void updateAccess(@MappingTarget Log log, AccessUpdateRequest request);

//    @Mapping(source = "staff.staffId", target = "staffId")
//    @Mapping(source = "permission", target = "permissionId")
    @Mapping(source = "version.id", target = "versionId")
    AccessUpdateResponse toAccessUpdateResponse(Log log);

//    @Mapping(source = "permission.id", target = "permissionId")
//    @Mapping(source = "staff.staffId", target = "staffId")
    @Mapping(source = "version.id", target = "versionId")
    AccessResponse toAccessResponse(Log log);

    List<AccessResponse> toAccessResponseList(List<Log> logs);


}
