package com.example.skptemp.domain.notification.event;

import com.example.skptemp.domain.cheer.dto.CheerItemEventRequest;
import com.example.skptemp.domain.item.entity.Item;
import com.example.skptemp.domain.item.entity.UserItem;
import com.example.skptemp.domain.item.repository.UserItemRepository;
import com.example.skptemp.domain.notification.dto.NotificationEventRequest;
import com.example.skptemp.domain.notification.dto.NotificationRequest;
import com.example.skptemp.domain.notification.entity.NotificationEntity;
import com.example.skptemp.domain.notification.repository.NotificationRepository;
import com.example.skptemp.domain.notification.service.NotificationService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final UserItemRepository userItemRepository;
    private final EntityManager entityManager;

    @EventListener
    @Transactional
    public void sendNotificationMessage(NotificationEventRequest event) {

        NotificationEntity notificationEntity = new NotificationEntity(event.getNotificationType(), event.getMessage(), event.getUserId(), false);
        notificationRepository.save(notificationEntity);
        notificationService.sendNotification(new NotificationRequest(event.getUserId(), event.getTitle(), event.getMessage()));
    }

    @EventListener
    @Transactional
    public void cheerItemEvent(CheerItemEventRequest request) {

        UserItem fromUserItem = userItemRepository.findByUserIdAndItemId(request.getFromUserId(), request.getItemId()).orElseThrow();
        UserItem toUserItem = userItemRepository.findByUserIdAndItemId(request.getToUserId(), request.getItemId()).orElse(UserItem.create(
                request.getToUserId(), entityManager.getReference(Item.class, request.getItemId())
        ));
        fromUserItem.setCount(fromUserItem.getCount() - 1);
        toUserItem.setCount(toUserItem.getCount() + 1);

        userItemRepository.save(fromUserItem);
        userItemRepository.save(toUserItem);

    }

}
