package com.example.skptemp.domain.item.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetUserItemResponse {
    private Long userId;
    private List<UserItemResult> result;

    @Builder
    public GetUserItemResponse(Long userId, List<UserItemResult> result){
        this.userId = userId;
        this.result = result;
    }
}
