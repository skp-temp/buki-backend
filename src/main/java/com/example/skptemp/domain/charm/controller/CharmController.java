package com.example.skptemp.domain.charm.controller;

import com.example.skptemp.domain.charm.request.CharmDailyGoalCompleteRequest;
import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.request.CharmUpdateRequest;
import com.example.skptemp.domain.charm.request.CreateCharmRequest;
import com.example.skptemp.domain.charm.response.CharmDailyGoalCompleteResponse;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;
import com.example.skptemp.domain.charm.response.CreateCharmResponse;
import com.example.skptemp.domain.charm.service.CharmService;
import com.example.skptemp.global.common.CustomResponse;
import com.example.skptemp.global.common.SecurityUtil;
import com.example.skptemp.global.constant.EmotionType;
import com.example.skptemp.global.error.GlobalSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/charms")
@RequiredArgsConstructor
public class CharmController {

    private final CharmService charmService;
    private final SecurityUtil securityUtil;

    @Operation(summary = "create charm", description = "부적 생성 API")
    @PostMapping
    public ResponseEntity<CustomResponse<CreateCharmResponse>> createCharm(@Valid @RequestBody CreateCharmRequest request) {
        Long userId = securityUtil.getUserIdFromContext();

        CreateCharmResponse response = charmService.createCharm(userId, request);
        return new ResponseEntity<>(
                CustomResponse.created(response),
                GlobalSuccessCode.CREATED.getStatus());
    }

    @Operation(summary = "daily goal complete", description = "일일 목표 달성 처리 API")
    @PostMapping("/completion")
    public ResponseEntity<CustomResponse<CharmDailyGoalCompleteResponse>> dailyGoalComplete(@RequestBody CharmDailyGoalCompleteRequest request) {
        Long userId = securityUtil.getUserIdFromContext();
        CharmDailyGoalCompleteResponse response = charmService.dailyGoalDone(
                request.getCharmId(),
                userId,
                EmotionType.get(request.getEmotionIndex()),
                request.getComment());

        return ResponseEntity.ok(
                CustomResponse.ok(response));
    }

    @Operation(summary = "getMyCharm", description = "부적 정보 조회 API")
    @GetMapping("/{charmId}")
    public ResponseEntity<CustomResponse<CharmDetailResponse>> getMyCharm(@PathVariable Long charmId) {
        Long userId = securityUtil.getUserIdFromContext();
        CharmDetailResponse charm = charmService.getCharm(charmId, userId);
        return ResponseEntity.ok(CustomResponse.ok(charm));
    }


    @PutMapping("/{charmId}/setting")
    @Operation(summary = "updateCharmSetting", description = "부적 설정 변경 API")
    public ResponseEntity<Object> updateCharmSetting(@PathVariable Long charmId, @Valid @RequestBody CharmSettingUpdateRequest request) {
        charmService.updateCharmSetting(charmId, request);

        return ResponseEntity.ok(CustomResponse.ok());
    }

    @PutMapping("/{charmId}")
    @Operation(summary = "updateCharm", description = "부적 아이템 장착 API")
    @Deprecated
    //TODO 이미지는 클라이언트에서 저장, 서버는 장착 아이템 리스트 정보 만을 저장
    public ResponseEntity<Object> updateCharm(@Valid @RequestBody CharmUpdateRequest request) {

        return ResponseEntity.ok().build();
    }

}
