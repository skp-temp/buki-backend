package com.example.skptemp.domain.notification.entity;

import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.domain.notification.dto.NotificationType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "notification")
public class NotificationEntity extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private String message;

    private Long userId;

    private Boolean isRead;

    @Setter
    private boolean isAccepted;

    private Long friendId;

    @Setter
    private String friendName;



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
