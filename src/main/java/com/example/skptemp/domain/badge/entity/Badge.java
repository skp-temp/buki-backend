package com.example.skptemp.domain.badge.entity;


import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.global.constant.BadgeType;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Badge extends BaseEntity {
    @Id
    @Column(name = "badge_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //TODO: condition, tipDescription 동일한지 확인 필요
    //TODO: 뱃지 달성 조건 확인하는 로직 고민 필요
    private String description;
    private String tip;
    private BadgeType badgeType;
    private boolean isValid = true;

    protected Badge(){}

    @Builder
    public Badge(String name, String description, String tip, BadgeType badgeType){
        this.name = name;
        this.description = description;
        this.tip = tip;
        this.badgeType = badgeType;
    }

    public void delete(){
        if(!isValid) throw new GlobalException(GlobalErrorCode.BADGE_ALREADY_DELETED);
        this.isValid = false;
    }
}
