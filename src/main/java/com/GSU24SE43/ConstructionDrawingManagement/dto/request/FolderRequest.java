package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderRequest {
    @NotBlank(message = "Name Cannot Be Blank")
    private String name;
    @NotBlank(message = "URL Cannot Be Blank")
    private String url;
}
