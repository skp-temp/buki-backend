package com.example.skptemp.domain.charm.entity;

import com.example.skptemp.global.constant.AlarmDayType;
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
public class Charm {
    @Builder
    public Charm(Long categoryId, Long characterId, String goal) {
        this.categoryId = categoryId;
        this.characterId = characterId;
        this.goal = goal;
    }
    @Builder
    public Charm(Long categoryId, Long characterId, String gol, boolean alarmOn, LocalDateTime alarmTime) {
        assertionAlarm(alarmOn, alarmTime);
        this.categoryId = categoryId;
        this.characterId = characterId;
        this.goal = goal;
        this.alarmOn = alarmOn;
        this.alarmTime = alarmTime;
    }

    @Id @Column(name = "charm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long categoryId;
    private Long characterId;
    private LocalDateTime createdAt;
    private int charmLevel;
    private String goal;
    private boolean alarmOn;
    private LocalDateTime alarmTime;
    private AlarmDayType alarmDayType;

    private void assertionAlarm(boolean alarmOn, LocalDateTime alarmTime){
        if(alarmOn && alarmTime == null){
            throw new GlobalException(GlobalErrorCode.CHARM_VALID_EXCEPTION);
        }
    }
}
