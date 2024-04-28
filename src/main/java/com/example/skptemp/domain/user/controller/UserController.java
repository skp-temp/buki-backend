package com.example.skptemp.domain.user.controller;

import com.example.skptemp.domain.user.dto.*;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.service.UserService;
import com.example.skptemp.global.common.CustomResponse;
import com.example.skptemp.global.configuration.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "400", description = "사용자 에러")
            }
    )
    @Operation(summary = "login", description = "Login 작업을 수행합니다.")
    @PostMapping("/login")
    public ResponseEntity<CustomResponse<LoginResponse>> doLogin(@RequestBody LoginRequest request){
        userService.findByLoginTypeAndAuthProviderId(request.loginType(), request.platformProviderId());

        LoginResponse loginResponse = userService.doLogin(request.loginType(), request.platformProviderId(), request.pushToken());

        return ResponseEntity.ok()
                .body(CustomResponse.ok(loginResponse));
    }

    @Operation(summary = "signup", description = "카카오 연동 회원 가입 API")
    @PostMapping("/sign-up")
    public ResponseEntity<CustomResponse<SignUpResponse>> doSignup(@RequestBody SignupRequest signupRequest){
        SignUpResponse signUpResponse = userService.doSignup(signupRequest);
        userService.findByLoginTypeAndAuthProviderId(signupRequest.loginType(), signupRequest.platformProviderId());

        return ResponseEntity.ok()
                .body(CustomResponse.ok(signUpResponse));
    }
    @Operation(summary = "changeUserName", description = "사용자 이름 변경 API")
    @PostMapping("/name")
    public ResponseEntity<CustomResponse<UserResponse>> changeUserName(@RequestBody UserChangeNameRequest request){
        UserResponse response = userService.changeUserName(request);
        return ResponseEntity.ok()
                .body(CustomResponse.ok(response));
    }

    //TODO: 삭제 예정
    @Operation(summary = "createToken100Days", description = "개발 용 access 토큰 발급 (유효 기간 100일)")
    @PostMapping("/create-token-100days")
    public ResponseEntity<CustomResponse<TokenResponse>> createToken100Days(){
        String jwt = userService.createJwt(1L);
        return ResponseEntity.ok()
                .body(CustomResponse.ok(new TokenResponse(jwt)));
    }

    @Operation(summary = "createToken10Min", description = "테스트 용 access 토큰 발급 (유효 기간 3시간)")
    @PostMapping("/create-token-10min")
    public ResponseEntity<CustomResponse<TokenResponse>> createToken3Hours(){
        String jwt = userService.createTestJwt();
        return ResponseEntity.ok()
                .body(CustomResponse.ok(new TokenResponse(jwt)));
    }

    @Operation(summary = "getUser", description = "사용자 정보 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<CustomResponse<UserResponse>> getUser(@PathVariable Long userId){
        UserResponse userResponse = userService.findById(userId);

        return ResponseEntity.ok()
                .body(CustomResponse.ok(userResponse));
    }

    @Operation(summary = "deleteUserForDev", description = "사용자 삭제, 개발용 입니다.")
    @DeleteMapping("/delete-for-dev")
    public ResponseEntity<CustomResponse<Void>> deleteUserForDev(@RequestBody Long userId){
        userService.deleteUser(userId);

        return ResponseEntity.ok()
                .body(CustomResponse.ok());
    }

    @Operation(summary = "deleteUser", description = "사용자 계정 탈퇴")
    @DeleteMapping
    public ResponseEntity<CustomResponse<Void>> deleteUser(HttpServletRequest request){
        final String authorization = jwtProvider.resolveTokenFromRequest(request);
        String token = jwtProvider.bearerRemove(authorization);

        Long userId = jwtProvider.getUserId(token);
        userService.deleteUser(userId);

        return ResponseEntity.ok()
                .body(CustomResponse.ok());
    }
}
