package com.example.skptemp.domain.item.service;

import com.example.skptemp.domain.item.entity.UserItem;

import java.util.Optional;

public interface UserItemService {
    Optional<UserItem> findByUserIdAndItemId(Long userId, Long itemId);
    UserItem getByUserIdAndItemId(Long userId, Long itemId);
    UserItem createUserItem(Long userId, Long itemId, int count);
}
