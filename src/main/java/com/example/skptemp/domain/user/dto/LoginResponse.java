package com.example.skptemp.domain.user.dto;

import com.example.skptemp.global.constant.LoginType;

public record LoginResponse(LoginType loginType, String authProviderId, String jwt) {
}
