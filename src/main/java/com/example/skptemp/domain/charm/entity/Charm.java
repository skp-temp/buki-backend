package com.example.skptemp.domain.charm.entity;

import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.global.constant.AlarmDayType;
import com.example.skptemp.global.constant.Category;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import com.example.skptemp.global.util.GlobalConstants;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Charm extends BaseEntity {
    @Builder
    public Charm(Long userId, Category category, String goal, Boolean alarmOn, AlarmDayType alarmDayType, LocalDateTime alarmTime) {
        assertionAlarm(alarmOn, alarmTime);
        this.userId = userId;
        this.category = category;
        this.goal = goal;
        this.alarmOn = alarmOn;
        this.alarmTime = alarmTime;
        this.alarmDayType = alarmDayType;
        this.charmLevel = 0;
        this.isValid = true;
    }

    @Id @Column(name = "charm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 부적 정보
    private Long userId;
    private Category category;
    private int charmLevel;
    private String goal;
    
    // 부적 알림 정보
    private Boolean alarmOn;
    private LocalDateTime alarmTime;
    @Enumerated(EnumType.STRING)
    private AlarmDayType alarmDayType;

    // 일일 목표 완료 여부 판단
    private boolean todayComplete;

    // 논리적 삭제 처리 여부 판단
    private boolean isValid;

    private void assertionAlarm(boolean alarmOn, LocalDateTime alarmTime){
        if(alarmOn && alarmTime == null){
            throw new GlobalException(GlobalErrorCode.CHARM_VALID_EXCEPTION);
        }
    }
    public void delete(){
        if(isValid) throw new GlobalException(GlobalErrorCode.CHARM_VALID_EXCEPTION);
        this.isValid = false;
    }
    public void levelUp(){
        if(this.charmLevel >= GlobalConstants.MAX_LEVEL) return;
        charmLevel++;
    }
    public void complete(){
        if(!todayComplete) {
            this.todayComplete = true;
            levelUp();
        }
        else
            throw new GlobalException(GlobalErrorCode.CHARM_TODAY_ALREADY_COMPLETE);
    }
}
