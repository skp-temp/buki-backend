package com.example.skptemp.domain.badge.entity;

import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class UserBadge extends BaseEntity {
    @Id @Column(name = "user_badge_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long badgeId;
    private Long userId;
    private LocalDateTime completedAt;
    private boolean isValid;

    protected UserBadge(){}

    public void delete(){
        if(!isValid) throw new GlobalException(GlobalErrorCode.BADGE_ALREADY_DELETED);
        isValid = false;
    }
}
