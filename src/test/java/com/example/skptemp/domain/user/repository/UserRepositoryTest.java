package com.example.skptemp.domain.user.repository;

import com.example.skptemp.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private static final String TEST_FIRST_NAME = "강";
    private static final String TEST_LAST_NAME = "동훈";
    private static final Long TEST_KAKAO_ID = 12341234L;

    @Test
    void 코드_생성_성공(){
        //given
        //when
        User testUser = User.createUser(TEST_KAKAO_ID);
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