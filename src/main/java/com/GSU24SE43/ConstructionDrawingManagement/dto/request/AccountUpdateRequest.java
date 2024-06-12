package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountUpdateRequest {
    @Size(min = 8, message = "User must be least 8 character")
    String username;
    @Size(min = 8, message = "Password must be least 8 character")
    String password;
//    String accountStatus;
//    String roleName;
}
