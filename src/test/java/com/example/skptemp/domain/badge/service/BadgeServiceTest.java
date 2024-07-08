package com.example.skptemp.domain.badge.service;

import com.example.skptemp.common.TestConstants;
import com.example.skptemp.domain.badge.dto.UserBadgeResult;
import com.example.skptemp.domain.badge.entity.Badge;
import com.example.skptemp.domain.badge.repository.BadgeRepository;
import com.example.skptemp.global.constant.BadgeType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class BadgeServiceTest {

    @Autowired BadgeService badgeService;
    @Autowired BadgeRepository badgeRepository;
    @AfterEach
    void afterEach(){
        badgeRepository.deleteAll();
    }
    @Test
    void 뱃지_생성_성공(){
        //given
        //when
        badgeService.createBadge(TestConstants.TEST_BADGE_NAME_1,
                TestConstants.TEST_BADGE_CONDITION_1,
                TestConstants.TEST_BADGE_TIP_DESCRIPTION_1,
                BadgeType.CHEER);
        //then
    }

    @Test
    void 사용자_뱃지_획득_정보_조회_성공(){
        //given
        Badge badge1 = Badge.builder()
                .name(TestConstants.TEST_BADGE_NAME_1)
                .badgeCondition(TestConstants.TEST_BADGE_CONDITION_1)
                .tipDescription(TestConstants.TEST_BADGE_TIP_DESCRIPTION_1)
                .badgeType(BadgeType.CHEER)
                .build();
        Badge badge2 = Badge.builder()
                .name(TestConstants.TEST_BADGE_NAME_2)
                .badgeCondition(TestConstants.TEST_BADGE_CONDITION_2)
                .tipDescription(TestConstants.TEST_BADGE_TIP_DESCRIPTION_2)
                .badgeType(BadgeType.CHEER)
                .build();

        badgeRepository.save(badge1);
        badgeRepository.save(badge2);

        badgeService.completeBadge(TestConstants.TEST_USER_ID,
                badge1.getId());
        badgeService.completeBadge(TestConstants.TEST_USER_ID,
                badge2.getId());
        //when
        List<UserBadgeResult> userBadgeInfo = badgeService.getUserBadgeInfo(TestConstants.TEST_USER_ID);

        //then
        assertThat(userBadgeInfo.size()).isEqualTo(2);
    }
}