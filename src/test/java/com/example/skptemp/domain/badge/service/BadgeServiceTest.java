package com.example.skptemp.domain.badge.service;

import com.example.skptemp.common.TestConstants;
import com.example.skptemp.domain.badge.dto.UserBadgeResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class BadgeServiceTest {

    @Autowired BadgeService badgeService;
    @AfterEach
    void afterEach() {
    }
    @Test
    void 뱃지_생성_성공(){
        //given
        //when
        //then
    }

    @Test
    void 사용자_뱃지_획득_정보_조회_성공(){
        //given
        //TODO: 뱃지를 Enum으로 관리하기 때문에, 테스트 코드 변경 필요
        //when
        List<UserBadgeResult> userBadgeInfo = badgeService.getUserBadgeInfo(TestConstants.TEST_USER_ID);

        //then
        assertThat(userBadgeInfo.size()).isEqualTo(2);
    }
}