package com.GSU24SE43.ConstructionDrawingManagement.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskChildCreateResponse {
    UUID id;
    UUID parentTaskId;
    String title;
    String description;
    String status;
    int priority;
    Date createDate;
    Date beginDate;
    Date endDate;
}
