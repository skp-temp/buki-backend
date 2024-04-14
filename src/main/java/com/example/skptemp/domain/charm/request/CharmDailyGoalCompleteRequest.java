package com.example.skptemp.domain.charm.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CharmDailyGoalCompleteRequest {
    private Long charmId;
    private int emotionIndex;
    private String comment;
}
