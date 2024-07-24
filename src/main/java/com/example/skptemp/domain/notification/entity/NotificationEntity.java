package com.example.skptemp.domain.notification.entity;

import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.domain.notification.dto.NotificationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NotificationEntity extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private NotificationType notificationType;

    private String message;

    private Long userId;

    private Boolean isRead;

    private Boolean isAccepted;



    @Builder
    public NotificationEntity(Long id, NotificationType notificationType, String message, Long userId, boolean isRead) {
        this.id = id;
        this.notificationType = notificationType;
        this.message = message;
        this.userId = userId;
        this.isRead = isRead;
    }

    public NotificationEntity(NotificationType notificationType, String message, Long userId, boolean isRead) {
        this.notificationType = notificationType;
        this.message = message;
        this.userId = userId;
        this.isRead = isRead;
    }
}
