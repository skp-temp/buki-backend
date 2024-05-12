package com.example.skptemp.domain.cheer;

import java.util.List;

public interface CheerCustomRepository {
    List<CheerCountResponse> getCheerCount(Long userId);
    List<CheerCountResponse> getCheeredCount(Long userId);
}
