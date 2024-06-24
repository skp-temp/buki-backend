package com.example.skptemp.domain.item.controller;

import com.example.skptemp.domain.item.dto.GetUserItemResponse;
import com.example.skptemp.domain.item.dto.GiveItemRequest;
import com.example.skptemp.domain.item.service.ItemService;
import com.example.skptemp.global.common.CustomResponse;
import com.example.skptemp.global.common.SecurityStaticUtil;
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
    public ResponseEntity<CustomResponse<GetUserItemResponse>> getUserItemList(){
        Long userId = SecurityStaticUtil.getUserId();
        GetUserItemResponse response = itemService.findItemListByUserId(userId);

        return ResponseEntity.ok(CustomResponse.ok(response));
    }

    @Operation(summary = "doGacha", description = "뽑기 API")
    @PostMapping("/gacha")
    public ResponseEntity<CustomResponse<Long>> doGacha(){
        Long itemId = itemService.gacha();
        return ResponseEntity.ok(CustomResponse.ok(itemId));
    }

    @Operation(summary = "giveItem for dev, manager", description = "개발 및 운영자용 아이템 지급 API")
    @PostMapping
    public ResponseEntity<CustomResponse<Void>> giveItem(@RequestBody GiveItemRequest request){
        itemService.giveItem(request);
        return ResponseEntity.ok(CustomResponse.ok());
    }
}
