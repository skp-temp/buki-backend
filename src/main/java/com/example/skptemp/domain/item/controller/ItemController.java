package com.example.skptemp.domain.item.controller;

import com.example.skptemp.domain.item.dto.GetUserItemResponse;
import com.example.skptemp.domain.item.service.ItemService;
import com.example.skptemp.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
@RestController
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "getUserItemList", description = "특정 사용자가 보유한 아이템 정보 조회 API")
    @GetMapping("/user-item-list") //TODO: paging 적용 필요
    public ResponseEntity<ApiResponse<GetUserItemResponse>> getUserItemList(@RequestParam Long userId){
        GetUserItemResponse response = itemService.findItemListByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    //TODO: 뽑기 API 개발
    @Operation(summary = "doGacha", description = "뽑기 API")
    @PostMapping("/gacha")
    public ResponseEntity<ApiResponse<Void>> doGacha(){
        return ResponseEntity.ok(ApiResponse.ok());
    }

    //TODO: 뽑기 가능 여부 조회 API 개발
    @Operation(summary = "getGachaCondition", description = "뽑기 가능 여부 조회")
    @GetMapping("/gacha-condition")
    public ResponseEntity<ApiResponse<Void>> getGachaCondition(){
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "giveItem for dev, manager", description = "개발 및 운영자용 아이템 지급 API")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> giveItem(Long userId, Long itemId, int count){
        itemService.giveItem(userId, itemId, count);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
