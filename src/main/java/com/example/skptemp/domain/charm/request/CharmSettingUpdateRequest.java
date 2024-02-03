package com.example.skptemp.domain.charm.request;

import com.example.skptemp.global.constant.AlarmDayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CharmSettingUpdateRequest {

    private AlarmDayType alarmDayType;
    private boolean alarmOn;
    private LocalDateTime alarmTime;



}
