package com.example.skptemp.domain.badge.service;

import com.example.skptemp.common.TestConstants;
import com.example.skptemp.domain.badge.dto.BadgeResult;
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
    void 뱃지_생성_성공(){ //TODO: service 객체는 뱃지 부여를 위한 수단을 제공하지 않는다. (필요할까??)
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
    void 갓생_부적_뱃지_부여_성공(){
        //given
        //when
        badgeService.refreshUserBadgeInfo(TestConstants.TEST_USER_ID); // data.sql을 통해 갓생 부적 5개의 완성된 부적 추가한 상태

        //then
        List<UserBadgeResult> userBadgeInfo = badgeService.getUserBadgeInfo(TestConstants.TEST_USER_ID);
        for(var userBadge : userBadgeInfo){
            log.info("badge 정보" + userBadge.badge().toString());
        }
    }

    @Test
    void 전체_뱃지_조회(){
        //given
        //when
        List<BadgeResult> resultList = badgeService.getAllBadges();
        //then
        for(var badgeResult : resultList)
            log.info(badgeResult.toString());
    }
}