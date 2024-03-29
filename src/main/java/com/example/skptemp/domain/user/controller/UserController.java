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

    @Operation(summary = "doLogin", description = "Login 작업을 수행합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> doLogin(HttpServletResponse response, LoginType loginType, String authProviderId){
        User findUser = userService.findByLoginTypeAndAuthProviderId(loginType, authProviderId);

        String jwt = userService.createJwt(findUser.getId());
        LoginResponse loginResponse = userService.doLogin(loginType, authProviderId, jwt);
        response.addHeader("authorization", jwt);

        return ResponseEntity.ok()
                .body(ApiResponse.ok(loginResponse));
    }

    @Operation(summary = "doSignUp", description = "카카오 연동 회원 가입 API")
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<SignUpResponse>> doSignup(HttpServletResponse response, LoginType loginType, String authProviderId){
        //TODO: 카카오 서버 통신 및 사용자 정보 저장 API + changeUserName 분리 하는 방식 적절한지 고민...
        SignUpResponse signUpResponse = userService.doSignup(loginType, authProviderId);

        User findUser = userService.findByLoginTypeAndAuthProviderId(loginType, authProviderId);
        String jwt = userService.createJwt(findUser.getId()); // 부키 DB 식별자 정보를 담아 jwt 발급 (access code)
        response.addHeader("authorization", jwt);

        return ResponseEntity.ok()
                .body(ApiResponse.ok(signUpResponse));
    }
    @Operation(summary = "changeUserName", description = "사용자 이름 변경 API")
    @PostMapping("/name") //TODO: 성, 이름 정보를 별도로 입력 받기 위한 별도의 API
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
