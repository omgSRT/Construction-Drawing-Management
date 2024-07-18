package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import com.GSU24SE43.ConstructionDrawingManagement.enums.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskChildCreateByHead_V2Request {

//    UUID departmentId;
//    UUID projectId;
    String title;
    String description;
    int priority;
    Date beginDate;
    Date endDate;
    List<UUID> staffs;

    List<Permission> permissions;
}
