package com.example.skptemp.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class NotificationMessageResponse {
    private Long id;

    private NotificationType notificationType;

    private String message;

    private Long userId;

    private boolean read;

    private String friendName;

    private Boolean isAccepted;
}
