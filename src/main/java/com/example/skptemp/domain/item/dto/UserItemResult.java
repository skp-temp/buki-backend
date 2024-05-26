package com.example.skptemp.domain.item.dto;

import com.example.skptemp.domain.item.entity.ItemType;
import com.example.skptemp.global.constant.Category;
import lombok.Builder;

public record UserItemResult(Long itemId, Category category, String itemName, ItemType itemType, int count) {
    @Builder
    public UserItemResult {
    }
}
