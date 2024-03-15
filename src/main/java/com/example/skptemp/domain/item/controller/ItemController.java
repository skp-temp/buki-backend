package com.example.skptemp.domain.item.controller;

import com.example.skptemp.domain.item.service.ItemService;
import com.example.skptemp.domain.item.service.UserItemService;
import com.example.skptemp.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
@RestController
public class ItemController {
    private final ItemService itemService;
    private final UserItemService userItemService;

    //TODO: Item API 개발 필요
    @GetMapping("/{item-id}")
    public ResponseEntity<ApiResponse<Void>> getItem(Long itemId){
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/user-item/{user-id}")
    public ResponseEntity<ApiResponse<Void>> getUserItem(Long userId){
        return ResponseEntity.ok(ApiResponse.ok());
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
