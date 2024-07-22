package com.GSU24SE43.ConstructionDrawingManagement.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractorResponse {
    UUID contractorId;
    String contractorName;
    String address;
    String phone;
    String email;
    int tax_identification_number;
    String business_license;
}
