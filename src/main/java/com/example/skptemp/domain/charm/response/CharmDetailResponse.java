package com.example.skptemp.domain.charm.response;

import com.example.skptemp.global.constant.Category;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Setter;

public class CharmDetailResponse {
    private Category category;
    private String goal;

    @Setter
    private long count;

    private Long characterId;

    //TODO: category, goal, level (도전 일수), 목표 달성 스탬프 (emotionType, comment), 응원 메시지

    @QueryProjection
    public CharmDetailResponse(Category category, String goal) {
        this.category = category;
        this.goal = goal;
    }


}
