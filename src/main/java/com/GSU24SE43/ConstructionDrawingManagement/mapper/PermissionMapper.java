package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.PermissionRequest;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    @Mapping(target = "accesses", ignore = true)
    void updatePermission(@MappingTarget Permission permission, PermissionRequest permissionRequest);
}
