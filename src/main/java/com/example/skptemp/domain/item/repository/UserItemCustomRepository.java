package com.example.skptemp.domain.item.repository;

import com.example.skptemp.domain.item.dto.CheerItemResponse;
import com.example.skptemp.global.constant.Category;

import java.util.List;

public interface UserItemCustomRepository {
    List<CheerItemResponse> getCheerItemList(Long userId, Category category);
}
