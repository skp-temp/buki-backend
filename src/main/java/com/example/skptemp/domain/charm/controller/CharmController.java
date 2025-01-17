package com.example.skptemp.domain.charm.controller;

import com.example.skptemp.domain.charm.dto.*;
import com.example.skptemp.domain.charm.request.CharmDailyGoalCompleteRequest;
import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.request.CreateCharmRequest;
import com.example.skptemp.domain.charm.response.CharmDailyGoalCompleteResponse;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;
import com.example.skptemp.domain.charm.response.CreateCharmResponse;
import com.example.skptemp.domain.charm.service.CharmService;
import com.example.skptemp.global.common.CustomResponse;
import com.example.skptemp.global.common.SecurityUtil;
import com.example.skptemp.global.error.GlobalSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/charms")
@RequiredArgsConstructor
public class CharmController {

    private final CharmService charmService;

    @Operation(summary = "create charm", description = "부적 생성 API")
    @PostMapping
    public ResponseEntity<CustomResponse<CreateCharmResponse>> createCharm(@Valid @RequestBody CreateCharmRequest request) {
        Long userId = SecurityUtil.getUserId();

        CreateCharmResponse response = charmService.createCharm(userId, request);
        return new ResponseEntity<>(
                CustomResponse.created(response),
                GlobalSuccessCode.CREATED.getStatus());
    }

    @Operation(summary = "daily goal complete", description = "일일 목표 달성 처리 API")
    @PostMapping("/complete")
    public ResponseEntity<CustomResponse<CharmDailyGoalCompleteResponse>> dailyGoalComplete(@RequestBody CharmDailyGoalCompleteRequest request) {
        Long userId = SecurityUtil.getUserId();

        CharmDailyGoalCompleteResponse response = charmService.dailyGoalComplete(
                request.getCharmId(),
                userId,
                request.getEmotion(),
                request.getComment());

        return ResponseEntity.ok(
                CustomResponse.ok(response));
    }

    @Operation(summary = "getMyCharm", description = "부적 정보 조회 API")
    @GetMapping("/{charmId}")
    public ResponseEntity<CustomResponse<CharmDetailResponse>> getMyCharm(@PathVariable Long charmId) {
        Long userId = SecurityUtil.getUserId();
        CharmDetailResponse response = charmService.getCharm(charmId, userId);

        return ResponseEntity.ok(CustomResponse.ok(response));
    }


    @PutMapping("/{charmId}/setting")
    @Operation(summary = "updateCharmSetting", description = "부적 설정 변경 API")
    public ResponseEntity<Object> updateCharmSetting(@PathVariable Long charmId, @Valid @RequestBody CharmSettingUpdateRequest request) {
        charmService.updateCharmSetting(charmId, request);

        return ResponseEntity.ok(CustomResponse.ok());
    }

    @GetMapping("/{charmId}/summary")
    @Operation(summary = "부적 요약 정보 조회", description = "요약 정보")

    public ResponseEntity<CustomResponse<CharmSummaryResponse>> getSummary(@PathVariable Long charmId) {

        return ResponseEntity.ok(CustomResponse.ok(charmService.getSummary(charmId)));

    }

    @GetMapping("/{charmId}/stamp")
    @Operation(summary = "목표 달성 스탬프 조회하기")

    public ResponseEntity<CustomResponse<List<StampResponse>>> getStamp(@PathVariable Long charmId) {

        return CustomResponse.okResponseEntity(charmService.getStamp(charmId));
    }

    @GetMapping("/{charmId}/items")
    @Operation(description = "부적이 장착할 수 있는 아이템 목록")
    public void getCharmEquipableItemList(@PathVariable Long charmId) {

        charmService.getEquipableItemList();
    }

    @GetMapping("/{charmId}/message")
    @Operation(description = "부적에 있는 응원 메시지 조회하기")
    public ResponseEntity<CustomResponse<List<CheerMessageResponse>>> getCheeringMessage(@PathVariable Long charmId) {
        return CustomResponse.okResponseEntity(charmService.getCheerMessage(charmId));
    }

    @PatchMapping("/modfiy")
    @Operation(description = "부적 아이템 장착 편집하기")
    public ResponseEntity<CustomResponse<Void>> itemCharmModify(@RequestBody ItemCharmRequest request) {

        charmService.itemCharmModify(request);
        return ResponseEntity.ok(CustomResponse.ok());
    }

    @GetMapping("/list")
    @Operation(description = "부적 전체 조회",summary = "전체 조회")
    public ResponseEntity<CustomResponse<CharmAllListResponse>> getCharmList(
            CharmSort sort
    ){

        CharmAllListResponse charmList = charmService.getCharmList(sort);

        return CustomResponse.okResponseEntity(charmList);
    }
}
