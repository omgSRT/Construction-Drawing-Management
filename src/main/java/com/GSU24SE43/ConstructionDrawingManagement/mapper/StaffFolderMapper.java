package com.GSU24SE43.ConstructionDrawingManagement.mapper;


import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffFolderCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffFolderUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffFolderViewResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.StaffFolder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;


@Mapper(componentModel = "spring")
public interface StaffFolderMapper {

    @Mapping(source = "staff.staffId", target = "staffId")
    @Mapping(source = "folder.id", target = "folderId")
    StaffFolderCreateResponse toResponse(StaffFolder staffFolder);

    StaffFolderUpdateResponse toUpdateResponse(StaffFolder staffFolder);

    @Mapping(source = "staff.staffId", target = "staffId")
    @Mapping(source = "folder.id", target = "folderId")
    StaffFolderViewResponse toStaffFolderViewResponse(StaffFolder staffFolder);
}
