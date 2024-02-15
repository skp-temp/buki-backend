package com.example.skptemp.domain.item.service;

import com.example.skptemp.domain.item.entity.Item;

import java.util.List;

public interface ItemService {
    List<Item> findItemListByUserId(Long userId); // 사용자의 아이템 리스트 조회
    int itemCount(Long userId, Long itemId); // 사용자가 보유한 특정 아이템 개수 조회
}
