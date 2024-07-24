package com.example.skptemp.domain.notification.service;

import com.example.skptemp.domain.notification.dto.NotificationMessageResponse;
import com.example.skptemp.domain.notification.dto.NotificationRequest;
import com.example.skptemp.domain.notification.dto.NotificationResponse;
import com.example.skptemp.domain.notification.dto.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    Page<NotificationMessageResponse> getNotificationMessage(NotificationType type, Pageable pageable);

    NotificationResponse sendNotification(NotificationRequest request);
}
