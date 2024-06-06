package com.example.skptemp.domain.charm.service;

import com.example.skptemp.domain.charm.dto.*;
import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.request.CreateCharmRequest;
import com.example.skptemp.domain.charm.response.CharmDailyGoalCompleteResponse;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;
import com.example.skptemp.domain.charm.response.CreateCharmResponse;
import com.example.skptemp.domain.statistics.StatisticsCategoryRankingResponse;
import com.example.skptemp.global.constant.EmotionType;

import java.util.List;

public interface CharmService {

    void itemCharmModify(ItemCharmRequest request);

    List<CheerMessageResponse> getCheerMessage(Long charmId);

    void getEquipableItemList();


    void completeToday(CompleteTodayRequest charmId);

    List<StampResponse> getStamp(Long charmId);


    CharmSummaryResponse getSummary(Long charmId);

    CreateCharmResponse createCharm(Long userId, CreateCharmRequest request);
    Charm findById(Long id);
    CharmDailyGoalCompleteResponse dailyGoalComplete(Long charmId, Long userId, EmotionType emotionType, String comment);
    CharmDetailResponse getCharm(Long charmId, Long userId);
    void updateCharm(Long charmId);
    void updateCharmSetting(Long charmId, CharmSettingUpdateRequest request);

    List<Charm> getOldestCharm(int size);

    StatisticsCategoryRankingResponse getCategoryRanking();

}
