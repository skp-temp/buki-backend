package com.example.skptemp.domain.item.entity;

import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.global.constant.Category;
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
    private Category category;
    private String itemName;
    private ItemType itemType;

    private Item(Category category, String itemName, ItemType itemType){
        this.category = category;
        this.itemName = itemName;
        this.itemType = itemType;
    }
    public static Item create(Category category, String itemName, ItemType itemType){
        return new Item(category, itemName, itemType);
    }
}
