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
public class TaskParentCreateRequest {
    UUID projectId;
//    UUID departmentId;
//    UUID accountId;
    String title;
    String description;
    Date beginDate;
    Date endDate;

}
