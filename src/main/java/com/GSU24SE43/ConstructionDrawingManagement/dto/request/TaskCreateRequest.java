package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskCreateRequest {
    UUID parentTask;
    UUID departmentId;
//    UUID projectId;
    String title;
    String description;
    String status;
    Date beginDate;
    Date endDate;
}
