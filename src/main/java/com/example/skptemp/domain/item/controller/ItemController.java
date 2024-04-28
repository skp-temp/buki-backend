package com.example.skptemp.domain.item.controller;

import com.example.skptemp.domain.item.dto.GetUserItemResponse;
import com.example.skptemp.domain.item.dto.GiveItemRequest;
import com.example.skptemp.domain.item.service.ItemService;
import com.example.skptemp.global.common.CustomResponse;
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
    public ResponseEntity<CustomResponse<GetUserItemResponse>> getUserItemList(@RequestBody Long userId){
        GetUserItemResponse response = itemService.findItemListByUserId(userId);
        return ResponseEntity.ok(CustomResponse.ok(response));
    }

    //TODO: 뽑기 API 개발
    @Operation(summary = "doGacha", description = "뽑기 API")
    @PostMapping("/gacha")
    public ResponseEntity<CustomResponse<Void>> doGacha(){
        return ResponseEntity.ok(CustomResponse.ok());
    }

    //TODO: 뽑기 가능 여부 조회 API 개발
    @Operation(summary = "getGachaCondition", description = "뽑기 가능 여부 조회")
    @GetMapping("/gacha-condition")
    public ResponseEntity<CustomResponse<Void>> getGachaCondition(){
        return ResponseEntity.ok(CustomResponse.ok());
    }

    @Operation(summary = "giveItem for dev, manager", description = "개발 및 운영자용 아이템 지급 API")
    @PostMapping
    public ResponseEntity<CustomResponse<Void>> giveItem(@RequestBody GiveItemRequest request){
        itemService.giveItem(request.userId(), request.itemId(), request.count());
        return ResponseEntity.ok(CustomResponse.ok());
    }
}
