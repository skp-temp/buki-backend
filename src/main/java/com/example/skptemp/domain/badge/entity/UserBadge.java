package com.example.skptemp.domain.badge.entity;

import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
public class UserBadge extends BaseEntity {
    @Id @Column(name = "user_badge_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Badge badge;
    private Long userId;
    private boolean isValid = true;
    private LocalDate completedAt;

    protected UserBadge(){}

    public void delete(){
        if(!isValid)
            throw new GlobalException(GlobalErrorCode.BADGE_ALREADY_DELETED);
        this.isValid = false;
    }

    @Builder
    public UserBadge(Badge badge, Long userId){
        this.badge = badge;
        this.userId = userId;
        this.completedAt = LocalDate.now();
    }
}
