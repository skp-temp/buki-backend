package com.example.skptemp.domain.charm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor

public class CharmAllListResponse {
    private List<CharmListResponse> complete;
    private int completeCount;
    private List<CharmListResponse> notComplete;
    private int notCompleteCount;

    public CharmAllListResponse(List<CharmListResponse> complete, List<CharmListResponse> notComplete) {
        this.complete = complete;
        this.completeCount = complete.size();
        this.notComplete = notComplete;
        this.notCompleteCount = notComplete.size();
    }
}
