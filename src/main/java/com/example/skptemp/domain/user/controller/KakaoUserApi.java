package com.example.skptemp.domain.user.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "kakaoUser", url = "https://kapi.kakao.com")
public interface KakaoUserApi {
    // 카카오 사용자 정보 가져오기 (access token 또는 admin key를 header에 담아 GET or POST로 요청)
    @GetMapping("/v2/user/me")
    ResponseEntity<String> getUserInfo(@RequestHeader Map<String, String> header);
}
