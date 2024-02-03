package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;

public interface QuerydslCharmRepository {
    CharmDetailResponse getDetail(Long charmId, Long userId);

    void settingUpdate(Long charmId, CharmSettingUpdateRequest request);
}
