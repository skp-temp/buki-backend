package com.example.skptemp.domain.notification.repository;

import com.example.skptemp.domain.notification.dto.NotificationType;
import com.example.skptemp.domain.notification.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationCustomRepository {
    Page<NotificationEntity> getNotificationMessageList(Pageable pageable, NotificationType notificationType, Long userId);
}
