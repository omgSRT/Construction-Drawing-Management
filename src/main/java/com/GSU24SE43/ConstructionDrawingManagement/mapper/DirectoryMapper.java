package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DirectoryRequest;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Directory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DirectoryMapper {
    Directory toFolder(DirectoryRequest request);

    @Mapping(target = "projects", ignore = true)
    void updateFolder(@MappingTarget Directory directory, DirectoryRequest request);
}
