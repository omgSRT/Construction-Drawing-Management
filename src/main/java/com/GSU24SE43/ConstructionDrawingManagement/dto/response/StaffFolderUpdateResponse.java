package com.GSU24SE43.ConstructionDrawingManagement.dto.response;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import com.GSU24SE43.ConstructionDrawingManagement.enums.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffFolderUpdateResponse {
    Staff staff;
    Folder folder;
    List<Permission> permissions;
}
