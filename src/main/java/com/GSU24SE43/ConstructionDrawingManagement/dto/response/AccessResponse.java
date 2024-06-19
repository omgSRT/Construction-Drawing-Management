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
public class AccessResponse {
    private UUID id;
    private Date dateTime ;
    private String URLLong;
    private UUID permissionId;
    private UUID versionId;
    private UUID staffId;
}
