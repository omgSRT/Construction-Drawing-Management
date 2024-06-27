package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.NotificationRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.NotificationResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Notification;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.NotificationMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.AccountRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.NotificationRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.TaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class NotificationService {
    final NotificationRepository notificationRepository;
    final NotificationMapper notificationMapper;
    final AccountRepository accountRepository;
    final TaskRepository taskRepository;
    final PaginationUtils paginationUtils = new PaginationUtils();

    public List<NotificationResponse> createNotification(NotificationRequest request) {
        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));

        List<Account> accountList = request.getAccountIds().stream()
                .map(id -> accountRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND)))
                .toList();

        List<Notification> newNotifications = accountList.stream().map(account -> {
            Notification newNotification = notificationMapper.toNotification(request);
            newNotification.setRead(false);
            newNotification.setTask(task);
            newNotification.setAccount(account);
            return notificationRepository.save(newNotification);
        }).toList();

        return newNotifications.stream()
                .map(notificationMapper::toNotificationResponse)
                .toList();
    }

    public List<NotificationResponse> getAllNotifications(int page, int perPage) {
        try {
            var notifications = notificationRepository.findAll().stream().map(notificationMapper::toNotificationResponse).toList();
            return paginationUtils.convertListToPage(page, perPage, notifications);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteNotificationById(UUID id){
        var notification = notificationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));
        notificationRepository.delete(notification);
    }

    public NotificationResponse findNotificationById(UUID id){
        return notificationMapper.toNotificationResponse(notificationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND)));
    }

    public NotificationResponse changeNotificationReadStatus(UUID id){
        var notification = notificationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));
        notification.setRead(!notification.isRead());
        return notificationMapper.toNotificationResponse(notificationRepository.save(notification));
    }

    public List<NotificationResponse> getNotificationsByAccountId(UUID accountId, int page, int perPage){
        var notificationResponses = notificationRepository.findByAccountAccountId(accountId).stream()
                .map(notificationMapper::toNotificationResponse)
                .toList();
        notificationResponses = paginationUtils.convertListToPage(page, perPage, notificationResponses);
        return notificationResponses;
    }
}
