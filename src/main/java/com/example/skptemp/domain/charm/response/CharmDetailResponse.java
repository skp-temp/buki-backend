package com.example.skptemp.domain.charm.response;

import com.example.skptemp.domain.charm.dto.ChallengeHistoryResult;
import com.example.skptemp.global.constant.Category;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class CharmDetailResponse {
    private Category category;
    private String goal;

    @Setter
    private long count;
    @Setter
    private List<ChallengeHistoryResult> challengeHistoryResultList;

    private Long characterId;

    //TODO: category, goal, level (도전 일수), 목표 달성 스탬프 (emotionType, comment), 응원 메시지

    @QueryProjection
    public CharmDetailResponse(Category category, String goal) {
        this.category = category;
        this.goal = goal;
    }


}
