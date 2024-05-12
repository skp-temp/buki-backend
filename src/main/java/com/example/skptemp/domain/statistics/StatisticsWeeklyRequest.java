package com.example.skptemp.domain.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatisticsWeeklyRequest {

    @Schema(description = "연도")
    @NotNull
    @Positive
    private Integer year;
    @Schema(description = "달")
    @Positive
    private Integer month;
    @Schema(description = "주차")
    @Positive
    private Integer week;

}
