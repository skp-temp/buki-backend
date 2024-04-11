package com.example.skptemp.domain.item.controller;

import com.example.skptemp.domain.item.dto.GetUserItemResponse;
import com.example.skptemp.domain.item.service.ItemService;
import com.example.skptemp.domain.item.service.UserItemService;
import com.example.skptemp.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
@RestController
public class ItemController {
    private final ItemService itemService;
    private final UserItemService userItemService;

    //TODO: Item API 개발 필요
    @GetMapping("/{item-id}")
    public ResponseEntity<ApiResponse<Void>> getItem(@PathVariable Long itemId){

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/user-item-list")
    public ResponseEntity<ApiResponse<GetUserItemResponse>> getUserItem(@RequestParam Long userId){
        GetUserItemResponse response = itemService.findItemListByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping("/gacha")
    public ResponseEntity<ApiResponse<Void>> doGacha(){
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/gacha-condition")
    public ResponseEntity<ApiResponse<Void>> getGachaCondition(){
        return ResponseEntity.ok(ApiResponse.ok());
    }

}
