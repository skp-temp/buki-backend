package com.example.skptemp.domain.item.dto;

import com.example.skptemp.domain.item.entity.ItemType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserItemResult {
    private Long itemId;
    private String itemName;
    private ItemType itemType;
    private int count;

    @Builder
    public UserItemResult(Long itemId, String itemName, ItemType itemType, int count){
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.count = count;
    }
}
