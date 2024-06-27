package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.NotificationRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.NotificationResponse;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
@Slf4j
public class NotificationController {
    final NotificationService notificationService;

    @Operation(summary = "Create Notifications", description = "Create New Notifications")
    @PostMapping(path = "/create")
    public ApiResponse<List<NotificationResponse>> createNotification(@RequestBody @Valid NotificationRequest request){
        return ApiResponse.<List<NotificationResponse>>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(notificationService.createNotification(request))
                .build();
    }

    @Operation(summary = "Get All Notifications", description = "Get All Notifications")
    @GetMapping(path = "/getall")
    public ApiResponse<List<NotificationResponse>> getAllNotifications(@RequestParam(defaultValue = "1") int page,
                                                                       @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<NotificationResponse>>builder()
                .entity(notificationService.getAllNotifications(page, perPage))
                .build();
    }

    @Operation(summary = "Get Notification", description = "Get Notification By ID")
    @GetMapping(path = "/get/{id}")
    public ApiResponse<NotificationResponse> getNotificationById(@PathVariable UUID id){
        return ApiResponse.<NotificationResponse>builder()
                .entity(notificationService.findNotificationById(id))
                .build();
    }

    @Operation(summary = "Delete Notification", description = "Delete Notification By ID")
    @DeleteMapping(path = "/delete/{id}")
    public ApiResponse<NotificationResponse> deleteNotificationById(@PathVariable UUID id){
        var notification = notificationService.findNotificationById(id);
        notificationService.deleteNotificationById(id);
        return ApiResponse.<NotificationResponse>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(notification)
                .build();
    }

    @Operation(summary = "Change Notification Status", description = "Change Notification Status By ID")
    @PutMapping(path = "/change/status/{id}")
    public ApiResponse<NotificationResponse> changeNotificationStatusById(@PathVariable UUID id){
        return ApiResponse.<NotificationResponse>builder()
                .message(SuccessReturnMessage.CHANGE_SUCCESS.getMessage())
                .entity(notificationService.changeNotificationReadStatus(id))
                .build();
    }

    @Operation(summary = "Get All Notifications", description = "Get All Notification By Account ID")
    @GetMapping(path = "/getall/by/account/{accountId}")
    public ApiResponse<List<NotificationResponse>> getAllNotificationsByAccountId(@PathVariable UUID accountId,
                                                                                  @RequestParam(defaultValue = "1") int page,
                                                                                  @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<NotificationResponse>>builder()
                .entity(notificationService.getNotificationsByAccountId(accountId, page, perPage))
                .build();
    }
}
