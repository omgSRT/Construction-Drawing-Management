package com.GSU24SE43.ConstructionDrawingManagement.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VersionResponse {
    UUID id;
    String description;
    String versionNumber;
    Date uploadDate;
    String status;
    String url;
}
