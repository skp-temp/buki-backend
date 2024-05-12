package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;

import java.util.List;

public interface CharmCustomRepository {
    CharmDetailResponse getDetail(Long charmId, Long userId);

    void settingUpdate(Long charmId, CharmSettingUpdateRequest request);

    List<Charm> getOldestCharm(Long userId, int size);


}
