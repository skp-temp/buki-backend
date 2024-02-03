package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.dto.SocialAuthResponse;
import com.example.skptemp.domain.user.dto.SocialUserResponse;
import com.example.skptemp.domain.user.dto.SocialUserResult;

import java.util.Map;

public interface LoginService {
    SocialAuthResponse getAccessToken(String authorizationCode);
    SocialUserResult getUserInfo(String accessToken);
}
