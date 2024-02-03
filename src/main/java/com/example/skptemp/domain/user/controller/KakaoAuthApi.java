package com.example.skptemp.domain.user.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoAuth", url = "https://kauth.kakao.com")
public interface KakaoAuthApi {
    // get login token with authorization token
    @GetMapping("/oauth/token")
    ResponseEntity<String> getAccessToken(
            @RequestParam("client_id") String client_id,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("grant_type") String grantType,
            @RequestParam("redirect_url") String redirectUri,
            @RequestParam("code") String authorizationCode
    );
}
