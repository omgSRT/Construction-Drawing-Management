package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FloorDetailCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FloorDetailUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.FloorDetailResponse;

import com.GSU24SE43.ConstructionDrawingManagement.entity.FloorDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FloorDetailMapper {
    FloorDetail toFloorDetail(FloorDetailCreateRequest request);

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    @Mapping(source = "project.description", target = "projectDescription")
    FloorDetailResponse toFloorDetailResponse(FloorDetail floorDetail);

    @Mapping(target = "project", ignore = true)
    void updateFloorDetail(@MappingTarget FloorDetail floorDetail, FloorDetailUpdateRequest request);
}
