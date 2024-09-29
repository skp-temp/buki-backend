package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.dto.CharmItemDetailResponse;

import java.util.List;

public interface CharmItemCustomRepository {
    List<CharmItemDetailResponse> getCharmItemList(List<Long> charmIdList);
}
