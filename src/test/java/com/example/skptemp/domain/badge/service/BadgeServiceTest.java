package com.example.skptemp.domain.badge.service;

import com.example.skptemp.common.TestConstants;
import com.example.skptemp.domain.badge.dto.UserBadgeResult;
import com.example.skptemp.domain.badge.entity.Badge;
import com.example.skptemp.domain.badge.entity.UserBadge;
import com.example.skptemp.domain.badge.repository.UserBadgeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
class BadgeServiceTest {
    @Autowired BadgeService badgeService;
    @Autowired UserBadgeRepository userBadgeRepository;

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
        //given (임의로 repository component에 추가해서 뱃지 획득 처리한다.)
        UserBadge userBadge = UserBadge.builder()
                .userId(TestConstants.TEST_USER_ID)
                .badge(Badge.Happiness)
                .build();
        userBadgeRepository.save(userBadge);

        //when
        List<UserBadgeResult> userBadgeInfo = badgeService.getUserBadgeInfo(TestConstants.TEST_USER_ID);
        UserBadgeResult expectedResult = userBadgeInfo.get(0);

        //then
        assertThat(userBadgeInfo.size()).isEqualTo(1);
        assertThat(expectedResult.badge()).isEqualTo(Badge.Happiness);
    }

    @Test
    void 전체_뱃지_조회(){
        //given
        //when
        List<Badge> badgeList = badgeService.getAllBadges();
        //then
        for(var badge : badgeList)
            log.info(badge.toString());
    }
}