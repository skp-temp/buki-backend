package com.example.skptemp.domain.user.controller;

import com.example.skptemp.domain.user.dto.*;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.service.UserService;
import com.example.skptemp.global.common.ApiResponse;
import com.example.skptemp.global.error.GlobalErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {
    private final UserService userService;

    @Operation(summary = "kakaoSocialLogin", description = "redirection 전용")
    @GetMapping("/kakao-login")
    public ResponseEntity<ApiResponse<SocialUserResponse>> doKakaoLogin(HttpServletResponse response, @RequestParam String code){
        SocialUserResponse socialUserResponse = userService.doSocialLogin(code);
        //TODO: 회원 가입 없이 로그인 한 경우 별도 처리 예정
        Long kakaoId = socialUserResponse.getSocialUserResult().getId();
        User userByKakaoId = userService.findByKakaoId(kakaoId);
        String jwt = userService.createJwt(userByKakaoId.getId());
        response.addHeader("authorization", jwt);

        return ResponseEntity.created(URI.create("/kakao-login"))
                .body(new ApiResponse<>(socialUserResponse));
    }

    @Operation(summary = "doKakaoSignup", description = "카카오 연동 회원 가입 API")
    @GetMapping("/kakao-sign-up")
    public ApiResponse<SocialUserResponse> doKakaoSignup(HttpServletResponse response, @RequestParam String code){
        //TODO: 카카오 서버 통신 및 사용자 정보 저장 API + changeUserName 분리 하는 방식 적절한지 고민...
        SocialUserResponse socialUserResponse = userService.doSocialSignup(code);
        Long kakaoId = socialUserResponse.getSocialUserResult().getId();
        User userByKakaoId = userService.findByKakaoId(kakaoId);
        String jwt = userService.createJwt(userByKakaoId.getId());
        response.addHeader("authorization", jwt);

        return new ApiResponse<>(socialUserResponse);
    }
    @Operation(summary = "changeUserName", description = "사용자 이름 변경 API")
    @PostMapping("/name") //TODO: 성, 이름 정보를 별도로 입력 받기 위한 별도의 API
    public ApiResponse<UserResponse> changeUserName(UserChangeNameRequest request){
        UserResponse response = userService.changeUserName(request);
        return new ApiResponse<>(response);
    }

    //TODO: 삭제 예정
    @Operation(summary = "createToken", description = "연동 로그인 과정 생략 후 임시 토큰 발급")
    @GetMapping("/create-token")
    public ApiResponse<TokenResponse> createToken(){
        String jwt = userService.createJwt(1L);
        return new ApiResponse<>(new TokenResponse(jwt));
    }
}
