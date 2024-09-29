package com.example.skptemp.domain.item.dto;

import lombok.Builder;

public record UserItemResult(Long itemId, int categoryId, String itemName, int itemType, int count) {
    @Builder
    public UserItemResult {
    }
}
