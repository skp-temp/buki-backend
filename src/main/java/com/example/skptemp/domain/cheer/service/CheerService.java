package com.example.skptemp.domain.cheer.service;

import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.repository.CharmRepository;
import com.example.skptemp.domain.cheer.dto.CheerCountResponse;
import com.example.skptemp.domain.cheer.dto.CheerItemEventRequest;
import com.example.skptemp.domain.cheer.dto.SendCheerRequest;
import com.example.skptemp.domain.cheer.entity.Cheer;
import com.example.skptemp.domain.cheer.repository.CheerRepository;
import com.example.skptemp.domain.notification.dto.NotificationEventRequest;
import com.example.skptemp.domain.notification.dto.NotificationType;
import com.example.skptemp.domain.notification.event.EventPublisher;
import com.example.skptemp.domain.notification.util.NotificationUtil;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.repository.UserRepository;
import com.example.skptemp.global.common.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheerService {

    private final CheerRepository cheerRepository;
    private final EventPublisher eventPublisher;
    private final CharmRepository charmRepository;
    private final UserRepository userRepository;

    public List<CheerCountResponse> getCheeringFriends() {


        return cheerRepository.getCheerCount(SecurityUtil.getUserId());
    }

    public List<CheerCountResponse> getCheeredFriends() {
        return cheerRepository.getCheeredCount(SecurityUtil.getUserId());
    }


    public void sendCheer( SendCheerRequest request) {
        Long userId = SecurityUtil.getUserId();
        Charm charm = charmRepository.findById(request.getCharmId()).orElseThrow();
        Long cheeredUserId = charm.getUserId();
        User cheeredUser = userRepository.findById(cheeredUserId).orElseThrow();
        String message = request.getMessage();
        int messageLength = message.length();
        String subStringMessage = message.substring(0, Math.min(messageLength, 10));
        NotificationEventRequest notificationEventRequest;
        String name = cheeredUser.getLastName() + cheeredUser.getFirstName();
        if (Optional.ofNullable(request.getItemId()).isEmpty()) {
            notificationEventRequest = new NotificationEventRequest(
                    String.format(NotificationUtil.CHEER_MESSAGE_WITH_ITEM_FORMAT, name, subStringMessage),
                    String.format(NotificationUtil.CHEER_MESSAGE_WITH_ITEM_FORMAT, name, subStringMessage),
                    "응원 메시지",
                    NotificationType.CHEER,
                    cheeredUserId,
                    name
            );
            eventPublisher.publishCheerItemEvent(new CheerItemEventRequest(userId, cheeredUserId, request.getItemId()));

        } else {
            cheerRepository.save(new Cheer(userId, request.getUserId(), message, request.getCharmId()));
            notificationEventRequest = new NotificationEventRequest(
                    String.format(NotificationUtil.CHEER_MESSAGE_FORMAT, name, subStringMessage),
                    String.format(NotificationUtil.CHEER_MESSAGE_FORMAT, name, subStringMessage),
                    "응원 메시지",
                    NotificationType.CHEER,
                    cheeredUserId,
                    name
            );
        }


        eventPublisher.publishNotification(notificationEventRequest);
    }
}