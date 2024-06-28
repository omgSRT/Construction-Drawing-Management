package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VersionUpdateRequest {
    String description;
    @NotBlank(message = "URL Cannot Be Blank")
    @Pattern(regexp = "^(http|https)://.*$", message = "URL must be valid")
    String url;
}
