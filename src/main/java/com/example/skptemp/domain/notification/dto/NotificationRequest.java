package com.example.skptemp.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@AllArgsConstructor
public class NotificationRequest {
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "알림 제목", example = "안녕하세요")
    private String title;

    @Schema(description = "알림 메시지", example = "새로운 알림이 도착했습니다!")
    private String message;
}
