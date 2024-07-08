package com.example.skptemp.domain.user.dto;

import lombok.Getter;

@Getter
public class GetGachaStatusResponse {
    private final boolean status;

    public GetGachaStatusResponse(boolean status){
        this.status = status;
    }
}
