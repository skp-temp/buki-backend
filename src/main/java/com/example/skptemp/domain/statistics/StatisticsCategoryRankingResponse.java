package com.example.skptemp.domain.statistics;


import com.example.skptemp.global.constant.Category;
import io.swagger.v3.oas.annotations.media.Schema;

public interface StatisticsCategoryRankingResponse {

    @Schema(description = "이름")
    Category getCategory();

    @Schema(description = "카테고리 카운트")
    long getCount();

}
