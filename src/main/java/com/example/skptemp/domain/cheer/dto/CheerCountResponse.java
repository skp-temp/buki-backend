package com.example.skptemp.domain.cheer.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CheerCountResponse {

    private Long count;

    private Long from;

    @Setter
    private String name;

    @QueryProjection
    public CheerCountResponse(Long count, Long from,String name) {
        this.count = count;
        this.from = from;
        this.name = name;
    }
}
