package com.example.skptemp.domain.cheer.repository;

import com.example.skptemp.domain.cheer.dto.CheerCountResponse;

import java.util.List;

public interface CheerCustomRepository {
    List<CheerCountResponse> getCheerCount(boolean isDesc, Long userId);
}