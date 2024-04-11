package com.example.skptemp.domain.item.service;

import com.example.skptemp.domain.item.dto.GetUserItemResponse;

public interface ItemService {
    //TODO: Response DTO 개발 및 적용 필요
    GetUserItemResponse findItemListByUserId(Long userId); // 사용자의 아이템 리스트 조회
    int getItemCount(Long userId, Long itemId); // 사용자가 보유한 특정 아이템 개수 조회
    int getTotalItemCount(Long userId);
    void transferItem(Long userId, Long friendId, Long itemId, Long count);
    Long giveItemToUser(Long itemId, Long userId);
}
