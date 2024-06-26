package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.NotificationRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.NotificationResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toNotification(NotificationRequest request);
    
    NotificationResponse toNotificationResponse(Notification notification);
}
