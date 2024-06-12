package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentUpdateRequest {
    @NotBlank(message = "Content Cannot Be Blank")
    String content;
}
