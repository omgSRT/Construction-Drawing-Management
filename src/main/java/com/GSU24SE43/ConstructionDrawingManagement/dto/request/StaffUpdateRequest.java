package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffUpdateRequest {
    UUID departmentId;
    String fullName;
    String email;
    String address;
    String phone;
    String certificate;
    String degree;
}
