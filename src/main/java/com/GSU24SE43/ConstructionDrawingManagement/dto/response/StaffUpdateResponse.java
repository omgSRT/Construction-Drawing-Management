package com.GSU24SE43.ConstructionDrawingManagement.dto.response;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffUpdateResponse {
    UUID staffId;
    UUID departmentId;
    String fullName;
    String email;
    String address;
    String phone;
    String certificate;
    String degree;
    boolean isSupervisor;
}
