package com.example.skptemp.domain.item.controller;

import com.example.skptemp.domain.item.service.ItemService;
import com.example.skptemp.domain.item.service.UserItemService;
import com.example.skptemp.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<Object> getItem(Long itemId){
        return null;
    }

    @GetMapping("/user-item/{user-id}")
    public ApiResponse<Object> getUserItem(Long userId){
        return null;
    }

    @PostMapping("/gacha")
    public ApiResponse<Object> doGacha(){
        return null;
    }

    @GetMapping("/gacha-condition")
    public ApiResponse<Boolean> getGachaCondition(){
        return null;
    }

}
