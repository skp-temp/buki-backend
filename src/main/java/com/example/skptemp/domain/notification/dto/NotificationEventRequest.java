package com.example.skptemp.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEventRequest {

    private String message;

    private String title;

    private NotificationType notificationType;

    private Long userId;

    private String userName;

}
