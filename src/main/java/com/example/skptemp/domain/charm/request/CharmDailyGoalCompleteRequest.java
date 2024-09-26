package com.example.skptemp.domain.charm.request;

import com.example.skptemp.global.constant.EmotionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CharmDailyGoalCompleteRequest {
    private Long charmId;
    private EmotionType emotion;
    private String comment;
}
