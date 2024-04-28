package com.example.skptemp.domain.item.dto;

public record GiveItemRequest(Long userId, Long itemId, int count) {
}
