package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Access;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccessMapper {

    AccessCreateResponse toAccessCreateResponse(Access access);
}
