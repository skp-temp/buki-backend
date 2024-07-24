package com.example.skptemp.domain.charm.service;

import com.example.skptemp.domain.charm.dto.*;
import com.example.skptemp.domain.charm.entity.ChallengeHistory;
import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.entity.CharmItem;
import com.example.skptemp.domain.charm.repository.ChallengeHistoryRepository;
import com.example.skptemp.domain.charm.repository.CharmItemRepository;
import com.example.skptemp.domain.charm.repository.CharmRepository;
import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.request.CreateCharmRequest;
import com.example.skptemp.domain.charm.response.CharmDailyGoalCompleteResponse;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;
import com.example.skptemp.domain.charm.response.CreateCharmResponse;
import com.example.skptemp.domain.cheer.repository.CheerRepository;
import com.example.skptemp.domain.item.repository.UserItemRepository;
import com.example.skptemp.domain.statistics.StatisticsCategoryRankingResponse;
import com.example.skptemp.global.common.SecurityStaticUtil;
import com.example.skptemp.global.constant.EmotionType;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CharmServiceImpl implements CharmService {

    private final CharmRepository charmRepository;
    private final CharmItemRepository charmItemRepository;
    private final ChallengeHistoryRepository challengeHistoryRepository;
    private final UserItemRepository userItemRepository;
    private final CheerRepository cheerRepository;

    @Override
    public void itemCharmModify(ItemCharmRequest request) {
//        CharmItem
        List<Long> itemIdList = request.getItemIdList();
        charmItemRepository.deleteByCharmId(request.getCharmId());
        List<CharmItem> charmItemList = itemIdList.stream().map(
                id -> new CharmItem(request.getCharmId(), id)
        ).toList();

        charmItemRepository.saveAll(charmItemList);
    }

    @Override
    public List<CheerMessageResponse> getCheerMessage(Long charmId) {
        return cheerRepository.findCheerMessage(charmId);
    }

    @Override
    public void getEquipableItemList() {

//
//        // TODO Item user 테이블 개발 후 작업
//        Long userId = SecurityStaticUtil.getUserId();
//        List<UserItem> byUserId = userItemRepository.findByUserId(userId);


    }

    @Override
    @Transactional

    public List<StampResponse> getStamp(Long charmId) {

        Long userId = SecurityStaticUtil.getUserId();
        List<ChallengeHistory> historyList = challengeHistoryRepository.findByUserIdAndCharmId(userId, charmId);

        List<StampResponse> stampResponsesList = new ArrayList<>(Collections.nCopies(21, new StampResponse()));
        for (int i = 0; i < historyList.size(); i++) {
            ChallengeHistory challengeHistory = historyList.get(i);
            stampResponsesList.set(i, new StampResponse(Optional.ofNullable(challengeHistory.getHistoryDate()).map(LocalDate::toString).orElse(""), challengeHistory.getEmotionType()));
        }

        return stampResponsesList;
    }

    @Override
    public CharmSummaryResponse getSummary(Long charmId) {
        Charm charm = charmRepository.findById(charmId).orElseThrow();
        return new CharmSummaryResponse(charm.getCategory(), charm.getGoal(), charm.getAlarmTime(), charm.getAlarmDayType());

    }

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

    @Override
    public List<Charm> getOldestCharm(int size) {
        Long userId = SecurityStaticUtil.getUserId();
        return charmRepository.getOldestCharm(userId, size);
    }

    @Override
    public StatisticsCategoryRankingResponse getCategoryRanking() {
        Long userId = SecurityStaticUtil.getUserId();
        return charmRepository.getCategoryRanking(userId);
    }
}
