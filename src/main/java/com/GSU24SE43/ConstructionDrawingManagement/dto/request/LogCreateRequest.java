package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogCreateRequest {
    String URLLong;
    UUID staffId;
//    UUID versionId;
}