package com.example.skptemp.domain.item.entity;

import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class UserItem extends BaseEntity {
    @Id @Column(name = "user_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long itemId;
    private int count;

    public void addItem(Long count){
        this.count += count;
    }
    public void removeItem(Long count){
        validate(count);
        this.count -= count;
    }

    private UserItem(Long userId, Long itemId, int count){
        this.userId = userId;
        this.itemId = itemId;
        this.count = count;
    }

    public static UserItem create(Long userId, Long itemId){
        return new UserItem(userId, itemId, 0);
    }

    private void validate(Long count){
        if(this.count < count) throw new GlobalException(GlobalErrorCode.ITEM_COUNT_EXCEPTION);
    }
}
