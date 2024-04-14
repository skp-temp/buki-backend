package com.example.skptemp.domain.charm.request;

import com.example.skptemp.global.constant.AlarmDayType;
import com.example.skptemp.global.constant.Category;

import java.time.LocalDateTime;

public record CreateCharmRequest(
        Category category,
        String goal,
        AlarmDayType alarmDayType,
        boolean alarmOn,
        LocalDateTime alarmTime
        ) {
}
