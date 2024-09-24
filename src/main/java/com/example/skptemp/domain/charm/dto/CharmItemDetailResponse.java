package com.example.skptemp.domain.charm.dto;

import com.example.skptemp.domain.item.entity.ItemType;
import com.example.skptemp.global.constant.Category;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;

@Getter
@NoArgsConstructor
public class CharmItemDetailResponse {

    private Long itemId;
    private Long charmId;
    private ItemType itemType;
    private String itemName;
    private Category category;

    @QueryProjection
    public CharmItemDetailResponse(Long itemId, Long charmId, ItemType itemType, String itemName, Category category) {
        this.itemId = itemId;
        this.charmId = charmId;
        this.itemType = itemType;
        this.itemName = itemName;
        this.category = category;
    }
}
