package com.example.skptemp.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEventRequest {

    // 알림함 저장용 string
    private String message;

    // 노티 보낼때 기기에 출력되는 메시지
    private String notificationMessage;

    private String title;

    private NotificationType notificationType;

    private Long userId;

    private String userName;

}
