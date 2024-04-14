package com.example.skptemp.domain.character.entity;

import com.example.skptemp.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Deprecated
@Getter
@Setter
@Entity
@Table(name = "character")
public class Character extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id", nullable = false)
    private Long id;
    private Long categoryId;
    private String name;
    private Long level;
    public Character(){}
}