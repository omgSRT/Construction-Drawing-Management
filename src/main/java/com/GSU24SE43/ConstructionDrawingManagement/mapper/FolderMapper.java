package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FolderRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FolderUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.FolderResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FolderMapper {
    Folder toFolder(FolderRequest request);

    FolderResponse toFolderResponse(Folder folder);

    @Mapping(target = "drawings", ignore = true)
    @Mapping(target = "project", ignore = true)
    void updateFolder(@MappingTarget Folder folder, FolderUpdateRequest request);
}
