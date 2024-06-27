package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.VersionCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.VersionUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.VersionResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Version;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VersionMapper {
    Version toVersion(VersionCreateRequest request);

    VersionResponse toVersionResponse(Version version);

    @Mapping(target = "logs", ignore = true)
    @Mapping(target = "drawing", ignore = true)
    void updateVersion(@MappingTarget Version version, VersionUpdateRequest request);
}
