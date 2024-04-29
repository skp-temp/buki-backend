package com.example.skptemp.domain.cheer;

import java.util.List;

public interface CheerCustomRepository {
    List<CheerCountResponse> getCheerCount(boolean isDesc, Long userId);
}
