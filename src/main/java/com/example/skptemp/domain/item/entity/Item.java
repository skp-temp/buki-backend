package com.example.skptemp.domain.item.entity;

import com.example.skptemp.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Item extends BaseEntity {
    @Id @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long categoryId;
    private String itemName;
    private ItemType itemType;

    private Item(Long categoryId, String itemName, ItemType itemType){
        this.categoryId = categoryId;
        this.itemName = itemName;
        this.itemType = itemType;
    }
    public static Item create(Long categoryId, String itemName, ItemType itemType){
        return new Item(categoryId, itemName, itemType);
    }
}
