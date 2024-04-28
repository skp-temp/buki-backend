package com.example.skptemp.domain.notification.service;

import com.example.skptemp.domain.notification.dto.NotificationRequest;
import com.example.skptemp.domain.notification.dto.NotificationResponse;

public interface NotificationService {
    NotificationResponse sendNotification(NotificationRequest request);
}
