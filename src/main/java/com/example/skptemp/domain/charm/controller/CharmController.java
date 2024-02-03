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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CharmController {

    private final CharmService charmService;


    @PostMapping("/charm")
    public ResponseEntity<ApiResponse<Void>> createCharm(@Valid @RequestBody CreateCharmRequest request) {

        charmService.createCharm(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PostMapping("/charm/{charmId}/completion")
    public ResponseEntity<ApiResponse<Void>> dailyGoalDone(@Valid @PathVariable Long charmId) {

        //TODO userId security에서 가져오는거 수정해야함
        Long userId = null;
        charmService.dailyGoalDone(charmId, userId);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/charm/{charmId}")
    public ResponseEntity<ApiResponse<CharmDetailResponse>> getMyCharm(@PathVariable Long charmId) {
        CharmDetailResponse charm = charmService.getCharm(charmId);
        return ResponseEntity.ok(new ApiResponse<>(charm));
    }


    @PutMapping("/charms/{charmId}/setting")
    @Operation(summary = "부적 설정 변경 API")
    public ResponseEntity<Object> updateCharmSetting(@PathVariable Long charmId, @Valid @RequestBody CharmSettingUpdateRequest request) {
        charmService.updateCharmSetting(charmId, request);

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PutMapping("/charms/{charmId}")
    @Operation(summary = "부적 아이템 등등 변경")
    //TODO 이미지 어떻게 뿌려야 할지 안정해서 나중에 해야함
    public ResponseEntity<Object> updateCharm(@Valid @RequestBody CharmUpdateRequest request) {

        return ResponseEntity.ok().build();
    }

}
