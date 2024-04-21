package com.example.skptemp.domain.charm.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CharmDailyGoalCompleteRequest {
    private Long charmId;
    private int emotionIndex;
    private String comment;
}
