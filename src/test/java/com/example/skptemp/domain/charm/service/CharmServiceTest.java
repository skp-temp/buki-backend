package com.example.skptemp.domain.charm.service;

import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.request.CreateCharmRequest;
import com.example.skptemp.domain.charm.response.CreateCharmResponse;
import com.example.skptemp.global.constant.AlarmDayType;
import com.example.skptemp.global.constant.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class CharmServiceTest {

    @Autowired
    CharmService charmService;

    private static final Long TEST_USER_ID      = 1L;
    private static final String TEST_GOAL               = "집가기";
    private static final boolean TEST_ALARM_ON          = false;
    private static LocalDateTime TEST_ALARM_TIME;

    @BeforeAll
    static void 테스트_데이터_생성(){
        TEST_ALARM_TIME = LocalDateTime.now();
    }

    @Test
    void 부적_생성_성공(){
        //given
        CreateCharmRequest request = new CreateCharmRequest(Category.BEAUTY, TEST_GOAL, AlarmDayType.EVERYDAY, TEST_ALARM_ON, TEST_ALARM_TIME);
        //when
        CreateCharmResponse response = charmService.createCharm(TEST_USER_ID, request);
        Charm charm = charmService.findById(response.getCharmId());
        //then
        Assertions.assertThat(charm).isNotNull();
        Assertions.assertThat(charm.getGoal()).isEqualTo(TEST_GOAL);
        Assertions.assertThat(charm.getCharmLevel()).isEqualTo(0);
        Assertions.assertThat(charm.getAlarmDayType()).isEqualTo(AlarmDayType.EVERYDAY);
        Assertions.assertThat(charm.getAlarmOn()).isEqualTo(TEST_ALARM_ON);
    }

    @Test
    void 일일_목표_완료_성공(){
        //given
//        charmService.
        //when
        //then
    }
}