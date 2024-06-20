package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccessCreateRequest {
    String URLLong;
    UUID staffId;
    Set<UUID> permissons;
//    UUID versionId;
}
