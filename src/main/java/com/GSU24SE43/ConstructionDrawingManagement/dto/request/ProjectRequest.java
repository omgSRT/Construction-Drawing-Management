package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectRequest {
    @NotBlank(message = "Name Cannot Be Blank")
    String name;
    String description;
    Date startDate;
    Date endDate;
    UUID departmentId;
    @Nullable
    UUID accountId;
    @Nullable
    UUID staffId;
}
