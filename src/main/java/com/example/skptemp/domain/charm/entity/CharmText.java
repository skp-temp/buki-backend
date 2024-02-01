package com.example.skptemp.domain.charm.entity;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//TODO: 굳이 DB에 저장 해야 할까??
@Getter
@NoArgsConstructor
@Entity
public class CharmText {
    private Long id;
    private Long categoryId;
    private String text;

    @Builder
    public CharmText(Long categoryId, String text){
        this.categoryId = categoryId;
        this.text = text;
    }
}
