package com.example.skptemp.domain.user.dto;

import com.example.skptemp.global.constant.LoginType;

public record LoginRequest(LoginType loginType, String platformProviderId) {
}
