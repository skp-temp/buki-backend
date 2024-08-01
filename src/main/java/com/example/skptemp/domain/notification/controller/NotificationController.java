package com.example.skptemp.domain.notification.controller;

import com.example.skptemp.domain.notification.dto.NotificationMessageResponse;
import com.example.skptemp.domain.notification.dto.NotificationRequest;
import com.example.skptemp.domain.notification.dto.NotificationResponse;
import com.example.skptemp.domain.notification.dto.NotificationType;
import com.example.skptemp.domain.notification.service.NotificationService;
import com.example.skptemp.global.common.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@RestController
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "sendNotiByUserID", description = "userID가 일치하는 유저에게 알림을 보냅니다.")
    @PostMapping("/send")
    public ResponseEntity<CustomResponse<NotificationResponse>> sendNotification(@RequestBody NotificationRequest request) {
        NotificationResponse response = notificationService.sendNotification(request);
        return ResponseEntity.ok()
                .body(CustomResponse.ok(response));
    }

    @GetMapping
    @Operation(description = "내 알림 메시지 조회")
    public ResponseEntity<CustomResponse<Page<NotificationMessageResponse>>> getNotificationMessage(@RequestParam(defaultValue = "ALL") NotificationType type,
                                                                                                    Pageable pageable
    ) {
        return CustomResponse.okResponseEntity(notificationService.getNotificationMessage(type, pageable));
    }

}
