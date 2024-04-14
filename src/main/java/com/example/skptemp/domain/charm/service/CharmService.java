package com.example.skptemp.domain.charm.service;

import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.request.CreateCharmRequest;
import com.example.skptemp.domain.charm.response.CharmDailyGoalCompleteResponse;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;
import com.example.skptemp.domain.charm.response.CreateCharmResponse;
import com.example.skptemp.global.constant.EmotionType;

public interface CharmService {
    CreateCharmResponse createCharm(Long userId, CreateCharmRequest request);
    Charm findById(Long id);

    CharmDailyGoalCompleteResponse dailyGoalDone(Long charmId, Long userId, EmotionType emotionType, String comment);

    CharmDetailResponse getCharm(Long charmId, Long userId);

    void updateCharm(Long charmId);

    void updateCharmSetting(Long charmId, CharmSettingUpdateRequest request);
}
