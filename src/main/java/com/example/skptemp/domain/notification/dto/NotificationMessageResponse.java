package com.example.skptemp.domain.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class NotificationMessageResponse {

    @Schema(description = "notification id ")
    private Long id;

    @Schema(description = "알림 타입")
    private NotificationType notificationType;

    @Schema(description = "메시지")
    private String message;

    @Schema(description = "사용자 id")
    private Long userId;

    @Schema(description = "읽은지 여부")
    private boolean read;

    @Schema(description = "친구일 경우 친구 이름")
    private String friendName;

    @Schema(description = "친구일 경우 수락 여부")
    private boolean friendAccepted;

    @Schema(description = "친구일 경우 프로필 이미지")
    private String friendProfileImg;


    public NotificationMessageResponse(NotificationType notificationType, Long id, String message, Long userId, boolean read, String friendName, boolean friendAccepted, String friendProfileImg) {
        this.notificationType = notificationType;
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.read = read;
        this.friendName = friendName;
        this.friendAccepted = friendAccepted;
        this.friendProfileImg = friendProfileImg;
    }
}
