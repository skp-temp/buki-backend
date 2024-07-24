package com.example.skptemp.domain.item.dto;

import com.example.skptemp.domain.item.entity.ItemType;
import com.example.skptemp.global.constant.Category;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CheerItemResponse {

    @QueryProjection
    public CheerItemResponse(int count, Long userId, String itemName, Category category, ItemType itemType) {
        this.count = count;
        this.userId = userId;
        this.itemName = itemName;
        this.category = category;
        this.itemType = itemType;
    }

    private int count;
    private Long userId;

    private String itemName;

    private Category category;
    private ItemType itemType;


}
