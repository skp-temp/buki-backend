package com.example.skptemp.domain.charm.dto;

import com.example.skptemp.global.constant.AlarmDayType;
import com.example.skptemp.global.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CharmSummaryResponse {

    private Category category;

    private String goal;

    private LocalDateTime alarmTime;
    private AlarmDayType alarmDayType;
}
