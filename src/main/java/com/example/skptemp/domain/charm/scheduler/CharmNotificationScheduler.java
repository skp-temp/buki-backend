package com.example.skptemp.domain.charm.scheduler;

import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.service.CharmService;
import com.example.skptemp.domain.notification.dto.NotificationRequest;
import com.example.skptemp.domain.notification.service.NotificationService;
import com.example.skptemp.global.constant.AlarmDayType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class CharmNotificationScheduler {

    private final CharmService charmService;
    private final NotificationService notificationService;

//    @Scheduled(cron = "0 * * * * ?")
    public void sendCharmNotification(){

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        List<AlarmDayType> alarmDayTypeList = new ArrayList<>();
        alarmDayTypeList.add(AlarmDayType.EVERYDAY);
        if (isWeekday(now)) {
            alarmDayTypeList.add(AlarmDayType.WEEKDAY);
        }
        List<Charm> charmList = charmService.findByAlarmTime(now, alarmDayTypeList);

        for (Charm charm : charmList) {
            CompletableFuture.runAsync(() -> {

                notificationService.sendNotification(new NotificationRequest(charm.getUserId(), "정기 알림", "메시지 "));
            });
        }


    }

    private boolean isWeekday(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

}

