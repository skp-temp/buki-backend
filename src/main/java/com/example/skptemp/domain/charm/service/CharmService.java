package com.example.skptemp.domain.charm.service;

import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.request.CreateCharmRequest;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;
import org.springframework.transaction.annotation.Transactional;

public interface CharmService {
    @Transactional
    void createCharm(CreateCharmRequest request);

    void dailyGoalDone(Long charmId, Long userId);

    CharmDetailResponse getCharm(Long charmId);

    void updateCharm(Long charmId);

    void updateCharmSetting(Long charmId, CharmSettingUpdateRequest request);
}
