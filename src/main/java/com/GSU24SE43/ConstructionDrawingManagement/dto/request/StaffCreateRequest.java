package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffCreateRequest {
    UUID accountId;
    UUID departmentId;
    String fullName;
    String email;
    String address;
    String phone;
    String Certificate;
    String Degree;

}
