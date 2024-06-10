package com.GSU24SE43.ConstructionDrawingManagement.dto.response;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Project;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
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
public class AccountUpdateStatusResponse {
    UUID accountId;
    String username;
    String password;
    Date createdDate;
    String accountStatus;
    String roleName;
    Staff staff;
    List<Project> projectList;
}
