package com.example.skptemp.domain.charm.dto;

import com.example.skptemp.global.constant.EmotionType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ChallengeHistoryResult {
    private Long id;
    private EmotionType emotionType;
    private String comment;
    private LocalDate historyDate;

    @QueryProjection
    public ChallengeHistoryResult(Long id, EmotionType emotionType, String comment, LocalDate historyDate) {
        this.id = id;
        this.emotionType = emotionType;
        this.comment = comment;
        this.historyDate = historyDate;
    }
}
