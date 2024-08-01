package com.example.skptemp.domain.charm.dto;

import com.example.skptemp.global.constant.Category;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CharmListResponse {

    private long completeCount;

    private Long charmId;
    private Boolean isComplete;
    private Category category;
    private String goal;

    @QueryProjection
    public CharmListResponse(long completeCount, Long charmId, Boolean isComplete, Category category, String goal) {
        this.completeCount = completeCount;
        this.charmId = charmId;
        this.isComplete = isComplete;
        this.category = category;
        this.goal = goal;
    }
}
