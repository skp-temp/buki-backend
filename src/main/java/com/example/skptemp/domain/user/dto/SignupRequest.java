package com.example.skptemp.domain.user.dto;

import com.example.skptemp.global.constant.LoginType;

public record SignupRequest(LoginType loginType, String authProviderId, String firstName, String lastName, String pushToken) {

}
