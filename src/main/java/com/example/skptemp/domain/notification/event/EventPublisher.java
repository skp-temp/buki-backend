package com.example.skptemp.domain.notification.event;

import com.example.skptemp.domain.cheer.dto.CheerItemEventRequest;
import com.example.skptemp.domain.notification.dto.NotificationEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;


    public void publishNotification(NotificationEventRequest request) {
        applicationEventPublisher.publishEvent(request);
    }

    public void publishCheerItemEvent(CheerItemEventRequest cheerItemEvent) {
        applicationEventPublisher.publishEvent(cheerItemEvent);
    }
}
