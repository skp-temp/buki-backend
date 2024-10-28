package com.example.skptemp.domain.badge.dto;

import com.example.skptemp.domain.badge.entity.Badge;
import com.example.skptemp.global.constant.BadgeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeDto {
    private int id;
    private String name;
    private String description;
    private String tip;
    private BadgeType badgeType;

    public BadgeDto(Badge badge){
        this.badgeType = badge.getBadgeType();
        this.id = badge.getId();
        this.tip = badge.getTip();
        this.description = badge.getDescription();
        this.name = badge.getName();
    }
}
