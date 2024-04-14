package com.example.skptemp.domain.charm.entity;

import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.global.constant.AlarmDayType;
import com.example.skptemp.global.constant.Category;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
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
    private Long userId;
    private Category category;
    private int charmLevel;
    private String goal;
    private Boolean alarmOn;
    private LocalDateTime alarmTime;
    @Enumerated(EnumType.STRING)
    private AlarmDayType alarmDayType;
    private boolean isValid;

    private void assertionAlarm(boolean alarmOn, LocalDateTime alarmTime){
        if(alarmOn && alarmTime == null){
            throw new GlobalException(GlobalErrorCode.CHARM_VALID_EXCEPTION);
        }
    }
    public void deleteCharm(){
        if(isValid) throw new GlobalException(GlobalErrorCode.CHARM_VALID_EXCEPTION);
        this.isValid = false;
    }
    public void levelUp(){
        if(this.charmLevel >= 21) return;
        charmLevel++;
    }
}
