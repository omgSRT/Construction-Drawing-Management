package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectUpdateRequest {
    @NotBlank(message = "Name Cannot Be Blank")
    String name;
    String description;
    Date startDate;
    Date endDate;
    @DecimalMin(value = "25", message = "Diện tích tối thiểu là 25 m2")
    double plotArea;
    UUID landPurposeId;
}
