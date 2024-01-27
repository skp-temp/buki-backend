package com.example.skptemp.domain.user.dto;

import lombok.Getter;

@Getter
public class TokenResponse {
    String jwt;

    public TokenResponse(String jwt){
        this.jwt = jwt;
    }
}
