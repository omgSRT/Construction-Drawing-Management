package com.GSU24SE43.ConstructionDrawingManagement.dto.response;


import com.GSU24SE43.ConstructionDrawingManagement.entity.DetailTask;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Version;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccessCreateResponse {
    UUID id;
    Date accessDateTime ;
    String descriptionLog;
    UUID detailTaskId;
    UUID versionId;
}
