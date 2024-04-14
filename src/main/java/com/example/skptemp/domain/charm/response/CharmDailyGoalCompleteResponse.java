package com.example.skptemp.domain.charm.response;

import com.example.skptemp.global.constant.EmotionType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CharmDailyGoalCompleteResponse {
    @Builder
    public CharmDailyGoalCompleteResponse(Long id, Long charmId, String comment, EmotionType emotionType) {
        this.id = id;
        this.charmId = charmId;
        this.comment = comment;
        this.emotionTypeIndex = emotionType.ordinal();
    }


    private final Long id;
    private final Long charmId;
    private final String comment;
    private final int emotionTypeIndex;
}
