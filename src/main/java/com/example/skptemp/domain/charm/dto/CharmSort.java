package com.example.skptemp.domain.charm.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public enum CharmSort {

    @Schema(description = "카테고리 순 정렬")
    CATEGORY,
    @Schema(description = "최신 완료순 정렬")
    RECENT,
    @Schema(description = "최근 생성순 정렬")
    CREATE

}
