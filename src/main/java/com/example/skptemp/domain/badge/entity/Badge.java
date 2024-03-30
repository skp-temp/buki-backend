package com.example.skptemp.domain.badge.entity;


import com.example.skptemp.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Badge extends BaseEntity {
    @Id
    @Column(name = "badge_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    protected Badge(){}
}
