package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DrawingRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DrawingUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DrawingResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Drawing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DrawingMapper {
    Drawing toDrawing(DrawingRequest request);

    DrawingResponse toDrawingResponse(Drawing drawing);

    void updateDrawing(@MappingTarget Drawing drawing, DrawingUpdateRequest request);
}
