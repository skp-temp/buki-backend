package com.example.skptemp.domain.user.repository;

import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.global.constant.LoginType;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private static final String TEST_LAST_NAME = "강";
    private static final String TEST_FIRST_NAME = "동훈";
    private static final LoginType TEST_LOGIN_TYPE = LoginType.KAKAO;
    private static final String TEST_AUTH_PROVIDER_ID = "123456789";
    private static final String TEST_PUSH_TOKEN = "..";
    @Test
    void 코드_생성_성공(){
        //given
        //when
        String profileImg= null;
        User testUser = User.createUser(TEST_LOGIN_TYPE, TEST_AUTH_PROVIDER_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_PUSH_TOKEN, profileImg);
        testUser.changeName(TEST_FIRST_NAME, TEST_LAST_NAME);
        userRepository.save(testUser);
        User findUser = userRepository.findById(testUser.getId()).get();

        //then
        log.info(testUser.getCode());
        Assertions.assertThat(testUser.getCode()).isNotEmpty();
        Assertions.assertThat(testUser.getAuthority()).isNotEmpty();
        Assertions.assertThat(testUser.getCode()).isEqualTo(findUser.getCode());
        Assertions.assertThat(testUser.getAuthority()).isEqualTo(findUser.getAuthority());
    }
}