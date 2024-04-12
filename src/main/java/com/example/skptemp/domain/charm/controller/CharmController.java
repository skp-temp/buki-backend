package com.example.skptemp.domain.charm.controller;

import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.request.CharmUpdateRequest;
import com.example.skptemp.domain.charm.request.CreateCharmRequest;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;
import com.example.skptemp.domain.charm.service.CharmService;
import com.example.skptemp.global.common.ApiResponse;
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


    @Operation(summary = "create charm", description = "부적 생성 API")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createCharm(@Valid @RequestBody CreateCharmRequest request) {
        charmService.createCharm(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "daily goal 완료", description = "일일 목표 달성 처리 API")
    @PostMapping("/{charmId}/completion")
    public ResponseEntity<ApiResponse<Void>> dailyGoalDone(@Valid @PathVariable Long charmId) {

        //TODO userId security에서 가져오는거 수정해야함
        Long userId = null;
        charmService.dailyGoalDone(charmId, userId);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "getMyCharm", description = "부적 정보 조회 API")
    @GetMapping("/{charmId}")
    public ResponseEntity<ApiResponse<CharmDetailResponse>> getMyCharm(@PathVariable Long charmId) {
        CharmDetailResponse charm = charmService.getCharm(charmId);
        return ResponseEntity.ok(ApiResponse.ok(charm));
    }


    @PutMapping("/{charmId}/setting")
    @Operation(summary = "updateCharmSetting", description = "부적 설정 변경 API")
    public ResponseEntity<Object> updateCharmSetting(@PathVariable Long charmId, @Valid @RequestBody CharmSettingUpdateRequest request) {
        charmService.updateCharmSetting(charmId, request);

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PutMapping("/{charmId}")
    @Operation(summary = "updateCharm", description = "부적 아이템 장착 API")
    @Deprecated
    //TODO 이미지 어떻게 뿌려야 할지 안정해서 나중에 해야함
    public ResponseEntity<Object> updateCharm(@Valid @RequestBody CharmUpdateRequest request) {

        return ResponseEntity.ok().build();
    }

}
