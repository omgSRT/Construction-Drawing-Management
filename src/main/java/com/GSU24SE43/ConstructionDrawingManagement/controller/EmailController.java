package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/email")
@Slf4j
public class EmailController {
    final EmailService emailService;

    @Operation(summary = "Send An Email to multiple participants")
    @PostMapping(value = "/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Void> sendMail(
            @RequestParam List<String> emails,
            @RequestParam(required = false) String body,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) List<MultipartFile> attachments) {
        emailService.sendEmail(emails, body, subject, attachments);
        return ApiResponse.<Void>builder()
                .message(SuccessReturnMessage.SEND_SUCCESS.getMessage())
                .build();
    }
}
