package com.example.skptemp.domain.item.entity;

import com.example.skptemp.domain.category.entity.Category;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Item {
    @Id @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long categoryId;
    private ItemType itemType;

    private Item(Long categoryId, ItemType itemType){
        this.categoryId = categoryId;
        this.itemType = itemType;
    }
    public static Item create(Long categoryId, ItemType itemType){
        return new Item(categoryId, itemType);
    }
}
