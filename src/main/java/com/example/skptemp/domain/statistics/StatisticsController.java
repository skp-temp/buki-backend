package com.example.skptemp.domain.statistics;

import com.example.skptemp.domain.cheer.dto.CheerCountResponse;
import com.example.skptemp.global.common.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
@Tag(name = "Statistics Controller", description = "분석 페이지 API")
public class StatisticsController {

    private final StatisticsFacadeService statisticsFacadeService;

    @GetMapping("/user")
    @Operation(description = "유저 통계 정보 가져 오기")
    public ResponseEntity<CustomResponse<StatisticsUserResponse>> getUserStatistics() {

        return ResponseEntity.ok(CustomResponse.ok(statisticsFacadeService.getUserStatistics()));
    }

    @GetMapping("/weekly")
    @Operation(description = "주간 요약 API, 예외 처리 코드 미완성", summary = "주간 요약")
    public ResponseEntity<CustomResponse<StatisticsWeeklyResponse>> getWeekly(
            @ModelAttribute StatisticsWeeklyRequest request
    ) {

        return ResponseEntity.ok(CustomResponse.ok(statisticsFacadeService.weeklySummary(request)));
    }

    @GetMapping("/category")
    @Operation(description = "카테고리 랭킹 가져오기")
    public ResponseEntity<CustomResponse<List<StatisticsCategoryRankingResponse>>> getCategoryRanking() {

        return ResponseEntity.ok(CustomResponse.ok(statisticsFacadeService.getCategoryRanking()));
    }

    @GetMapping("/emotions")
    @Operation(description = "가장 많이 남긴 감정 표현 가져오는 API")
    public ResponseEntity<CustomResponse<MostEmotionResponse>> getMostEmotions() {

        return ResponseEntity.ok(CustomResponse.ok(statisticsFacadeService.getMostEmotions()));
    }

    @GetMapping("/friends/cheered")
    @Operation(description = "나를 가장많이 응원 해준 친구 랭킹")
    public ResponseEntity<CustomResponse<List<CheerCountResponse>>> getCheeringStatistics() {

        return ResponseEntity.ok(CustomResponse.ok(statisticsFacadeService.getCheeredFriends()));

    }

    @GetMapping("/friends/cheering")
    @Operation(description = "내가 가장많이 응원 해준 친구 랭킹")

    public ResponseEntity<CustomResponse<List<CheerCountResponse>>> getCheeredStatistics(){

        return ResponseEntity.ok(CustomResponse.ok(statisticsFacadeService.getCheeringFriends()));
    }
}

