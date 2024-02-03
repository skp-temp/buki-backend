package com.example.skptemp.domain.badge.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Badge {
    @Id
    @Column(name = "badge_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    protected Badge(){}
}
