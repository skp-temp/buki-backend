package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.dto.LoginResponse;
import com.example.skptemp.domain.user.dto.SignUpResponse;
import com.example.skptemp.domain.user.dto.SignupRequest;
import com.example.skptemp.global.configuration.JwtProvider;
import com.example.skptemp.global.constant.LoginType;
import com.example.skptemp.global.error.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    JwtProvider jwtProvider;

    final Long USER_ID = 1L;

    final String TEST_AUTH_PROVIDER_ID_KAKAO = "kakao_test";
    final String TEST_AUTH_PROVIDER_ID_NAVER = "naver_test";
    final String TEST_AUTH_PROVIDER_ID_APPLE = "apple_test";
    final String TEST_AUTH_PROVIDER_ID_APPLE_FOR_DEV = "apple_test1";
    final String TEST_PUSH_TOKEN = "..";
    final String TEST_FIRST_NAME = "강";
    final String TEST_LAST_NAME = "동훈";
    @Test
    void 토큰_발급_성공(){
        //given
        //when
        String jwt = userService.createJwt(USER_ID);
        //then
        log.info("jwt: " + jwt);
        assertThat(jwt).isNotEqualTo("");
    }
    
    @Test
    void 토큰_파싱_성공(){
        //given
        String jwt = userService.createJwt(USER_ID);
        //when
        Long userId = jwtProvider.getUserId(jwt);
        //then
        assertThat(userId).isEqualTo(USER_ID);
    }

    @Test
    void 회원가입_성공(){
        //given
        //when
        SignUpResponse signUpResponse = userService.doSignup(
                new SignupRequest(LoginType.KAKAO, TEST_AUTH_PROVIDER_ID_KAKAO, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_PUSH_TOKEN)
        );

        //then
        assertThat(signUpResponse.loginType()).isEqualTo(LoginType.KAKAO);
        assertThat(signUpResponse.authProviderId()).isEqualTo(TEST_AUTH_PROVIDER_ID_KAKAO);
    }

    @Test
    void 로그인_성공(){
        //given
        userService.doSignup(
                new SignupRequest(LoginType.APPLE, TEST_AUTH_PROVIDER_ID_APPLE, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_PUSH_TOKEN)
        );

        //when
        LoginResponse loginResponse = userService.doLogin(LoginType.APPLE, TEST_AUTH_PROVIDER_ID_APPLE, userService.createJwt(USER_ID));
        //then
        assertThat(loginResponse.loginType()).isEqualTo(LoginType.APPLE);
        assertThat(loginResponse.authProviderId()).isEqualTo(TEST_AUTH_PROVIDER_ID_APPLE);
    }

    @Test
    void 사용자_삭제_성공(){
        //given
        SignUpResponse signUpResponse = userService.doSignup(
                new SignupRequest(LoginType.APPLE, TEST_AUTH_PROVIDER_ID_APPLE_FOR_DEV, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_PUSH_TOKEN)
        );
        String jwt = signUpResponse.jwt();
        Long userId = jwtProvider.getUserId(jwt);

        //when
        userService.deleteUser(userId);

        //then
        Assertions.assertThrows(GlobalException.class, () -> userService.findById(userId));
    }

    @Test
    void 사용자_삭제_중복_삭제_실패(){
        //given
        SignUpResponse signUpResponse = userService.doSignup(
                new SignupRequest(LoginType.APPLE, TEST_AUTH_PROVIDER_ID_APPLE_FOR_DEV, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_PUSH_TOKEN)
        );
        String jwt = signUpResponse.jwt();
        Long userId = jwtProvider.getUserId(jwt);

        //when
        userService.deleteUser(userId);

        //then
        Assertions.assertThrows(GlobalException.class, () -> userService.deleteUser(userId)); // 중복 삭제한 경우 Exception Throw
    }
}