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
    private String grade;
    private int count;
    private int wearingLevel;

    public void addItem(Long count){
        this.count += count;
    }

    public void removeItem(Long count){
        this.count -= count;
    }

    private void validateItemCount(Long count){
        if(this.count < count) throw new GlobalException(GlobalErrorCode.ITEM_VALID_EXCEPTION);
    }
}
