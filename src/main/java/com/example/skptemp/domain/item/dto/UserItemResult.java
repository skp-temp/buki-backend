package com.example.skptemp.domain.item.dto;

import com.example.skptemp.domain.item.entity.ItemType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserItemResult {
    private Long itemId;
    private Long categoryId;
    private String itemName;
    private ItemType itemType;
    private int count;

    @Builder
    public UserItemResult(Long itemId, Long categoryId, String itemName, ItemType itemType, int count){
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.count = count;
    }
}
