package com.example.skptemp.domain.charm.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Setter;

public class CharmDetailResponse {
    private String category;
    private String goal;

    @Setter
    private long count;

    private Long characterId;

    @QueryProjection
    public CharmDetailResponse(String category, String goal, Long characterId) {
        this.category = category;
        this.goal = goal;
        this.characterId = characterId;
    }


}
