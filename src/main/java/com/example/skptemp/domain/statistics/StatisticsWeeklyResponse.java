package com.example.skptemp.domain.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsWeeklyResponse {

    @Schema(description = "최대 8개까지 담고 있음")
    private List<StatisticsWeeklyItem> summary;



    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatisticsWeeklyItem{

        @Schema(description = "도전 이름")
        private String name;
        @Schema(description = "월 화 수 목 금 토 일 순으로 도전 여부를 담고 있음")
        private List<Boolean> isChallenged;


    }
}
