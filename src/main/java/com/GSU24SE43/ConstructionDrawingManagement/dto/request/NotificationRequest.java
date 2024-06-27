package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationRequest {
    @NotBlank(message = "Title Cannot Be Blank")
    String title;
    @NotBlank(message = "URL Cannot Be Blank")
    @Pattern(regexp = "^(http|https)://.*$", message = "URL must be valid")
    String url;
    @NotBlank(message = "Message Cannot Be Blank")
    String message;
    List<UUID> accountIds;
    UUID taskId;
}
