package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {
    String customerName;
    String address;
    @Pattern(regexp = "\\d+", message = "TIN must be numeric")
    @Size(min = 8, max = 14, message = "Sô điện thoại tối thiểu 8 số và tối đa 14 số")
    String phone;
    @Email
    String email;
}
