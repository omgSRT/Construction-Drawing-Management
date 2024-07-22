package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
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
    @Digits(integer = 13, fraction = 0, message = "TIN max is 13 number")
    int tax_identification_number;
    String business_license;
}
