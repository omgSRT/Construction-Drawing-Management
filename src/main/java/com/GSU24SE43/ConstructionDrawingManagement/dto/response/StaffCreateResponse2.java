package com.GSU24SE43.ConstructionDrawingManagement.dto.response;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffCreateResponse2 {
    UUID staffId;
    String fullName;
    String email;
    String address;
    String phone;
    String certificate;
    String degree;
    @JsonIgnoreProperties(value = {"staffList","taskList","departmentProjects"}, allowSetters = true)
    Department department;
//    boolean isSupervisor;

}
