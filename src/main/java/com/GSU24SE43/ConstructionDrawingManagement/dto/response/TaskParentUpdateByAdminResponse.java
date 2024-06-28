package com.GSU24SE43.ConstructionDrawingManagement.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskParentUpdateByAdminResponse {
//    UUID projectId;
//    UUID departmentId;
    String title;
    String description;
    Date beginDate;
    Date endDate;

}
