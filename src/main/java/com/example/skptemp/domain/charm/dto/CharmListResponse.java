package com.example.skptemp.domain.charm.dto;

import com.example.skptemp.domain.charm.entity.CharmItem;
import com.example.skptemp.domain.item.entity.UserItem;
import com.example.skptemp.global.constant.Category;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
public class CharmListResponse {

    private long completeCount;
    private Long charmId;
    private Boolean isComplete;
    private Category category;
    private String goal;

    @Setter
    private List<CharmItemDetailResponse> itemList;

    @QueryProjection
    public CharmListResponse(long completeCount, Long charmId, Boolean isComplete, Category category, String goal) {
        this.completeCount = completeCount;
        this.charmId = charmId;
        this.isComplete = isComplete;
        this.category = category;
        this.goal = goal;
    }
}
