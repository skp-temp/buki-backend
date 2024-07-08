package com.example.skptemp.domain;

import com.example.skptemp.domain.charm.dto.CompleteTodayRequest;
import com.example.skptemp.domain.charm.entity.ChallengeHistory;
import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.repository.ChallengeHistoryRepository;
import com.example.skptemp.domain.charm.repository.CharmRepository;
import com.example.skptemp.domain.charm.service.ChallengeHistoryService;
import com.example.skptemp.domain.cheer.entity.Cheer;
import com.example.skptemp.domain.user.service.UserService;
import com.example.skptemp.global.configuration.JwtProvider;
import com.example.skptemp.global.constant.AlarmDayType;
import com.example.skptemp.global.constant.Category;
import com.example.skptemp.global.constant.EmotionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.apache.http.auth.AUTH;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class CharmControllerTest {


    @Autowired
    MockMvc mockMvc;
    public static final String AUTHORIZATION = "Authorization";
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    CharmRepository charmRepository;
    @Autowired
    EntityManager entityManager;

    @Autowired
    private ChallengeHistoryService historyService;

    @Autowired
    private ChallengeHistoryRepository challengeHistoryRepository;
    private String token;

    private String url = "http://localhost:8080/";
    private Charm charm;

    @BeforeEach
    void setup() {
        // create charm
        charm = new Charm(3L, Category.DIET, "다이어트 짱짱 걸", false, AlarmDayType.WEEKDAY, LocalDateTime.now());
        entityManager.persist(charm);
        ChallengeHistory challengeHistory = new ChallengeHistory(3L, charm.getId(), LocalDate.now(), EmotionType.JOY, "즐거워!");
        entityManager.persist(challengeHistory);

        // create-token
        UserDetails userDetails = userDetailsService.loadUserByUsername("1");
        token = jwtProvider.createTestToken(userDetails);

        Cheer cheer = new Cheer(1L, 3L, "안뇽", charm.getId());
        entityManager.persist(cheer);

    }

    @Test
    @DisplayName("부적 요약 정보 조회 API")
    void charmSummary() throws Exception {

        mockMvc.perform(get(url + "/api/v1/charms/{charmId}/summary", charm.getId())
                        .header(AUTHORIZATION, token))
                .andDo(print());

    }

    @Test
    @DisplayName("목표 달성 스탬프 조회하기")
    void dailyStamp() throws Exception {
        mockMvc.perform(get(url + "/api/v1/charms/{charmId}/stamp", charm.getId())
                        .header(AUTHORIZATION, token))
                .andDo(print());

    }

    @Test
    @DisplayName("오늘 목표 달성")
    void dailyDone() throws Exception {
        CompleteTodayRequest request = new CompleteTodayRequest(EmotionType.JOY, charm.getId(), "즐거워!");
        mockMvc.perform(post(url + "/api/v1/charms/today", charm.getId())
                        .header(AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());
    }

    @Test
    @DisplayName("응원 메시지 조회하기")
    void getMessage() throws Exception {
        mockMvc.perform(get(url + "/api/v1/charms/{charmId}/message", charm.getId())
                        .header(AUTHORIZATION, token))
                .andDo(print());

    }
}
