package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import com.GSU24SE43.ConstructionDrawingManagement.enums.Role;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountCreateRequest {
    @Size(min = 8, message = "User must be least 8 character")
    String username;
    @Size(min = 8, message = "Password must be least 8 character")
    String password;

}
