package com.example.skptemp.domain.badge.entity;


import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Badge extends BaseEntity {
    @Id
    @Column(name = "badge_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String condition;
    private String tipDescription;
    private boolean isValid;

    protected Badge(){}
}
