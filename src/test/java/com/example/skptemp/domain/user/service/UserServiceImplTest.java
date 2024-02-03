package com.example.skptemp.domain.user.service;

import com.example.skptemp.global.configuration.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserService userService;
    @Autowired
    JwtProvider jwtProvider;

    final Long USER_ID = 1L;

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
        Long userId = Long.parseLong(jwtProvider.getUserId(jwt));
        //then
        assertThat(userId).isEqualTo(USER_ID);
    }
}