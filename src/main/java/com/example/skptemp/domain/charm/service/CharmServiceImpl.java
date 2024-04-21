package com.example.skptemp.domain.charm.service;

import com.example.skptemp.domain.charm.entity.ChallengeHistory;
import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.repository.ChallengeHistoryRepository;
import com.example.skptemp.domain.charm.repository.CharmRepository;
import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.request.CreateCharmRequest;
import com.example.skptemp.domain.charm.response.CharmDailyGoalCompleteResponse;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;
import com.example.skptemp.domain.charm.response.CreateCharmResponse;
import com.example.skptemp.global.constant.EmotionType;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CharmServiceImpl implements CharmService {

    private final CharmRepository charmRepository;
    private final ChallengeHistoryRepository challengeHistoryRepository;


    @Override
    @Transactional
    public CreateCharmResponse createCharm(Long userId, CreateCharmRequest request) {
        Charm charm = Charm.builder()
                .category(request.category())
                .goal(request.goal())
                .alarmDayType(request.alarmDayType())
                .alarmOn(request.alarmOn())
                .alarmTime(request.alarmTime())
                .build();
        charmRepository.save(charm);
        return new CreateCharmResponse(charm.getId());
    }

    @Override
    public Charm findById(Long id) {
        return charmRepository.findByIdAndIsValidIsTrue(id)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.CHARM_VALID_EXCEPTION));
    }

    @Override
    @Transactional
    public CharmDailyGoalCompleteResponse dailyGoalComplete(Long charmId, Long userId, EmotionType emotionType, String comment) {
        LocalDate date = LocalDate.now();

        // 일일 목표 완료 처리
        Charm charm = findById(charmId);
        charm.complete();

        ChallengeHistory challengeHistory = ChallengeHistory.builder()
                .historyDate(date)
                .userId(userId)
                .charmId(charmId)
                .emotionType(emotionType)
                .comment(comment)
                .build();
        challengeHistoryRepository.save(challengeHistory);

        return new CharmDailyGoalCompleteResponse(challengeHistory.getId(), charmId, charm.getCharmLevel());
    }

    @Override
    public CharmDetailResponse getCharm(Long charmId, Long userId) {
        return charmRepository.getDetail(charmId, userId);
    }

    @Transactional
    @Override
    public void updateCharm(Long charmId) {
        assertCharm(charmId);
        //TODO: 개발 필요
    }

    @Transactional
    @Override
    public void updateCharmSetting(Long charmId, CharmSettingUpdateRequest request) {

        charmRepository.settingUpdate(charmId, request);
    }

    private void assertCharm(Long charmId){
        charmRepository.findById(charmId);
    }
}
