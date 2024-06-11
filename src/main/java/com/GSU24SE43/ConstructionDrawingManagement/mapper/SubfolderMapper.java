package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.SubfolderRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.SubfolderUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.SubfolderResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Subfolder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubfolderMapper {
    Subfolder toSubfolder(SubfolderRequest request);

    SubfolderResponse toSubfolderResponse(Subfolder subfolder);

    @Mapping(target = "drawings", ignore = true)
    @Mapping(target = "project", ignore = true)
    void updateSubfolder(@MappingTarget Subfolder subfolder, SubfolderUpdateRequest request);
}
