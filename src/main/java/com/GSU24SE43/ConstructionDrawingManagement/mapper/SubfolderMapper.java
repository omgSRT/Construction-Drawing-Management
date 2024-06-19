package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.SubfolderRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.SubfolderUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.SubfolderResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubfolderMapper {
    Folder toSubfolder(SubfolderRequest request);

    SubfolderResponse toSubfolderResponse(Folder folder);

    @Mapping(target = "drawings", ignore = true)
    @Mapping(target = "project", ignore = true)
    void updateSubfolder(@MappingTarget Folder folder, SubfolderUpdateRequest request);
}
