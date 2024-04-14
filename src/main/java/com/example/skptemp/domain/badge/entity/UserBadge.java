package com.example.skptemp.domain.badge.entity;

import com.example.skptemp.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class UserBadge extends BaseEntity {
    @Id @Column(name = "user_badge_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long badgeId;
    private Long userId;

    protected UserBadge(){}
}
