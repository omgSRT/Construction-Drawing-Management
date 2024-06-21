package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.EmailRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @PostMapping(value = "/sendmail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<EmailRequest> sendMail(@RequestBody @Valid EmailRequest request){
        emailService.sendEmail(request);
        return ApiResponse.<EmailRequest>builder()
                .message(SuccessReturnMessage.SEND_SUCCESS.getMessage())
                .entity(request)
                .build();
    }
}
