package com.example.skptemp.domain.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsUserResponse {

    @Schema(description = "부키와 함께한지 xx일째 나타내는 값")
    private long userDays;
}
