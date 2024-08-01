package com.example.skptemp.domain.charm.dto;

import com.example.skptemp.global.constant.Category;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CharmListResponse {

    private long completeCount;
    private Boolean isComplete;
    private Category category;
    private String goal;

    @QueryProjection
    public CharmListResponse(int completeCount, Boolean isComplete, Category category, String goal) {
        this.completeCount = completeCount;
        this.isComplete = isComplete;
        this.category = category;
        this.goal = goal;
    }
}
