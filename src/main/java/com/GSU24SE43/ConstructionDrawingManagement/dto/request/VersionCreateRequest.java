package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VersionCreateRequest {
    String description;
    @NotBlank(message = "URL Cannot Be Blank")
    @Pattern(regexp = "^(http|https)://.*$", message = "URL must be valid")
    String url;
    UUID drawingId;
}
