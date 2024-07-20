package com.GSU24SE43.ConstructionDrawingManagement.dto.response;

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
public class TaskResponseDesigner {
    UUID id;
    UUID parentTaskId;
    UUID projectId;
    UUID departmentId;
    String title;
    String description;
    String status;
    int priority;
    Date createDate;
    Date beginDate;
    Date endDate;
    Set<UUID> staffIds;
}
