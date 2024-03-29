package com.example.skptemp.domain.user.dto;

import com.example.skptemp.global.constant.LoginType;

public record SignUpResponse(LoginType loginType, String authProviderId, String jwt) {
}
