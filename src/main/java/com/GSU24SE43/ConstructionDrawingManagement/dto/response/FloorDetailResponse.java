package com.GSU24SE43.ConstructionDrawingManagement.dto.response;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FloorDetailResponse {
    UUID id;
    int floorNumber;
    double floorArea;
    double height;
    int numberOfRooms;
    double occupancyRate;
    UUID projectId;
    String projectName;
    String projectDescription;
}
