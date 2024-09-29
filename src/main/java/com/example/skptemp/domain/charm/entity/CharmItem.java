package com.example.skptemp.domain.charm.entity;

import com.example.skptemp.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class CharmItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long charmId;
    private Long itemId;


    public CharmItem(Long charmId, Long itemId) {
        this.charmId = charmId;
        this.itemId = itemId;
    }
}
