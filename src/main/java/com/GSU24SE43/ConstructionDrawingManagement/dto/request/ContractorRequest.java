package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractorRequest {
    String contractorName;
    String address;
    String phone;
    @Email
    String email;
    @Size(max = 13, message = "TIN max is 13 number")
    int tax_identification_number;
    String business_license;
}
