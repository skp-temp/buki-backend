package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.controller.KakaoAuthApi;
import com.example.skptemp.domain.user.controller.KakaoUserApi;
import com.example.skptemp.domain.user.dto.SocialAuthResponse;
import com.example.skptemp.domain.user.dto.SocialUserResponse;
import com.example.skptemp.domain.user.dto.SocialUserResult;
import com.example.skptemp.global.util.GsonLocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService{
    private final KakaoAuthApi kakaoAuthApi;
    private final KakaoUserApi kakaoUserApi;
    @Value("${social.client.kakao.rest-api-key}")
    private String kakaoAppKey;
    @Value("${social.client.kakao.secret-key}")
    private String kakaoAppSecretKey;
    @Value("${social.client.kakao.redirect-url")
    private String kakaoRedirectUri;
    @Value("${social.client.kakao.grant_type}")
    private String kakaoGrantType;

    @Override
    public SocialAuthResponse getAccessToken(String authorizationCode) {
        ResponseEntity<String> response = kakaoAuthApi.getAccessToken(
                kakaoAppKey,
                kakaoAppSecretKey,
                kakaoGrantType,
                kakaoRedirectUri,
                authorizationCode
        );
        log.info("kakao auth response {}", response.toString());

        // parsing json string to java object
        return new Gson()
                .fromJson(
                        String.valueOf(response.getBody()),
                        SocialAuthResponse.class
                );
    }

    @Override
    public SocialUserResult getUserInfo(String accessToken) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + accessToken);
        log.info("access token: {}", accessToken);
        ResponseEntity<String> responseEntity = kakaoUserApi.getUserInfo(headerMap);

        log.info("kakao user response: {}", responseEntity.toString());
        String jsonString = responseEntity.getBody().toString();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter())
                .create();

        return gson.fromJson(jsonString, SocialUserResult.class);
    }
}
