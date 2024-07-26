package com.GSU24SE43.ConstructionDrawingManagement.dto.response;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import com.GSU24SE43.ConstructionDrawingManagement.enums.Permission;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskChildCreateByHeadResponse {
    UUID id;
    UUID projectId;
    UUID departmentId;
    String title;
    String description;
    String status;
    int priority;
    Date createDate;
    Date beginDate;
    Date endDate;
    List<Permission> permissions;
    @JsonIgnoreProperties(value = {"account","projects","detailTasks","comments","supervisor","staff_folders"}, allowSetters = true)
    List<Staff> staffs;

}
