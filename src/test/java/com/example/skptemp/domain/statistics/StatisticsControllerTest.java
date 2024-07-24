package com.example.skptemp.domain.statistics;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@Rollback(value = false)
@AutoConfigureMockMvc
class StatisticsControllerTest {

    public static final String AUTHORIZATION = "Authorization";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;
    @Autowired
    CharmRepository charmRepository;
    @Autowired
    EntityManager entityManager;

    @Autowired
    private ChallengeHistoryService historyService;

    @Autowired
    private ChallengeHistoryRepository challengeHistoryRepository;
    private String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInVzZXJfaWQiOjMsImlhdCI6MTcxNDk4OTgwMCwiZXhwIjoxNzIzNjI5ODAwLCJzdWIiOiIzIn0.GzMV01hfn1YTJG_DPT-zAr39n3XKY0AfSScjKM_DKv0";

    private String url = "http://localhost:8080/";
    private Charm charm;

    @BeforeEach
    void setUp() {

        token = "Bearer " + userService.createJwt(3L);


        charm = charmRepository.save(Charm.builder()
                .userId(3L)
                .category(Category.BEAUTY)
                .goal("테스트용 부적")
                .alarmOn(true)
                .alarmDayType(AlarmDayType.EVERYDAY)
                .alarmTime(LocalDateTime.now())
                .build()
        );
        charmRepository.save(Charm.builder()
                .userId(3L)
                .category(Category.DIET)
                .goal("테스트용 부적")
                .alarmOn(true)
                .alarmDayType(AlarmDayType.EVERYDAY)
                .alarmTime(LocalDateTime.now())
                .build()
        );
        challengeHistoryRepository.save(new ChallengeHistory(3L, charm.getId(), LocalDate.now(), EmotionType.JOY, "기록 1"));

        entityManager.persist(new Cheer(3L, 5L, "message 1",1L));
        entityManager.persist(new Cheer(3L, 6L, "message 2",1L));
        entityManager.persist(new Cheer(3L, 7L, "message 3",1L));

        entityManager.persist(new Cheer(5L, 3L, "message 4",1L));
        entityManager.persist(new Cheer(5L, 3L, "message 5",1L));
    }

    @Test
    @DisplayName("유저 통계 정보 가져오기")
    void userStatistics() throws Exception {


        mockMvc.perform(get(url + "statistics/user")
                        .header(AUTHORIZATION, token))
                .andDo(print());

    }

    @Test
    @DisplayName("주간 요약")
    void 주간요약() throws Exception {

        LocalDateTime now = LocalDateTime.now();
        LocalDate firstDayOfMonth = LocalDate.of(now.getYear(), now.getMonthValue(), 1);


        // WeekFields 객체를 생성합니다.
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        // 해당 월의 첫째 날부터 몇 번째 주인지 가져옵니다.
        int weekNumber = now.get(weekFields.weekOfMonth());

        mockMvc.perform(get(url + "statistics/weekly")
                        .param("year", String.valueOf(now.getYear()))
                        .param("month", String.valueOf(now.getMonthValue()))
                        .param("week", String.valueOf(weekNumber))
                        .header(AUTHORIZATION, token))
                .andDo(print());

    }

    @Test
    @DisplayName("카테고리 랭킹")
    void categoryRanking() throws Exception {

        mockMvc.perform(get(url + "statistics/category")
                        .header(AUTHORIZATION, token))
                .andDo(print());

    }


    @Test
    @DisplayName("나를 가장 많이 응원해주는 친구 랭킹")
    void cheeredRanking() throws Exception {

        mockMvc.perform(get(url + "statistics/friends/cheered")
                        .header(AUTHORIZATION, token))
                .andDo(print());

    }
    @Test
    @DisplayName("내가 가장 많이 응원해주는 친구 랭킹")
    void cheeringRanking() throws Exception {

        mockMvc.perform(get(url + "statistics/friends/cheering")
                        .header(AUTHORIZATION, token))
                .andDo(print());

    }
}