package com.example.skptemp.domain.user.controller;

import com.example.skptemp.domain.user.dto.*;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.service.UserService;
import com.example.skptemp.global.common.ApiResponse;
import com.example.skptemp.global.constant.LoginType;
import com.example.skptemp.global.error.GlobalErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;

    @Operation(summary = "login", description = "Login 작업을 수행합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> doLogin(HttpServletResponse response, LoginRequest request){
        User findUser = userService.findByLoginTypeAndAuthProviderId(request.loginType(), request.authProviderId());

        String jwt = userService.createJwt(findUser.getId());
        LoginResponse loginResponse = userService.doLogin(request.loginType(), request.authProviderId(), jwt);
        response.addHeader("authorization", jwt);

        return ResponseEntity.ok()
                .body(ApiResponse.ok(loginResponse));
    }

    @Operation(summary = "signup", description = "카카오 연동 회원 가입 API")
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<SignUpResponse>> doSignup(HttpServletResponse response, @RequestBody SignupRequest signupRequest){
        SignUpResponse signUpResponse = userService.doSignup(signupRequest);

        User findUser = userService.findByLoginTypeAndAuthProviderId(signupRequest.loginType(), signupRequest.authProviderId());
        String jwt = userService.createJwt(findUser.getId()); // 부키 DB 식별자 정보를 담아 jwt 발급 (access code)
        response.addHeader("authorization", jwt);

        return ResponseEntity.ok()
                .body(ApiResponse.ok(signUpResponse));
    }
    @Operation(summary = "changeUserName", description = "사용자 이름 변경 API")
    @PostMapping("/name")
    public ResponseEntity<ApiResponse<UserResponse>> changeUserName(@RequestBody UserChangeNameRequest request){
        UserResponse response = userService.changeUserName(request);
        return ResponseEntity.ok()
                .body(ApiResponse.ok(response));
    }

    //TODO: 삭제 예정
    @Operation(summary = "createToken", description = "연동 로그인 과정 생략 후 임시 토큰 발급")
    @GetMapping("/create-token")
    public ResponseEntity<ApiResponse<TokenResponse>> createToken(){
        String jwt = userService.createJwt(1L);
        return ResponseEntity.ok()
                .body(ApiResponse.ok(new TokenResponse(jwt)));
    }

    @Operation(summary = "getUser", description = "사용자 정보 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(Long userId){
        UserResponse userResponse = userService.findById(userId);

        return ResponseEntity.ok()
                .body(ApiResponse.ok(userResponse));
    }
}
