package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import com.GSU24SE43.ConstructionDrawingManagement.enums.DrawingStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DrawingStatusChangeRequest {
    UUID drawingId;
}
