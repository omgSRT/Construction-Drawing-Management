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
public class TaskParentCreateByHeadResponse {
    UUID id;
    UUID projectId;
    UUID departmentId;
    String title;
    String description;
    String status;
    Date createDate;
    Date beginDate;
    Date endDate;

}
