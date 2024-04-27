package com.example.skptemp.domain.notification.service;

import com.example.skptemp.domain.notification.dto.NotificationRequest;
import com.example.skptemp.domain.notification.dto.NotificationResponse;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final FirebaseMessaging firebaseMessaging;

    @Transactional(readOnly = true)
    @Override
    public NotificationResponse sendNotification(NotificationRequest request) {
        log.info("사용자 ID로 사용자 검색 중: {}", request.getUserId());
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        if (user.getPushToken() == null || user.getPushToken().isEmpty()) {
            log.warn("사용자 ID {}에 대한 푸시 토큰이 없습니다.", request.getUserId());
            return new NotificationResponse("Failed", "푸시 토큰이 없습니다.");
        }

        try {
            // 메시지 구성
            Message message = Message.builder()
                    .setToken(user.getPushToken())
                    .setNotification(Notification.builder()
                            .setTitle(request.getTitle())
                            .setBody(request.getMessage())
                            .build())
                    .build();

            // 메시지 전송
            String response = firebaseMessaging.send(message);
            log.info("알림이 성공적으로 전송되었습니다. 응답: {}", response);
            return new NotificationResponse("Success", "알림이 성공적으로 전송되었습니다.");
        } catch (Exception e) {
            log.error("사용자 ID {}에 알림 전송 실패: {}", request.getUserId(), e);
            return new NotificationResponse("Failed", "알림 전송 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
