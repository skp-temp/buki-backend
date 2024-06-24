package com.example.skptemp.domain.charm.service;

import com.example.skptemp.domain.charm.entity.ChallengeHistory;
import com.example.skptemp.domain.charm.repository.ChallengeHistoryRepository;
import com.example.skptemp.domain.statistics.MostEmotionResponse;
import com.example.skptemp.global.common.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeHistoryService {

    private final ChallengeHistoryRepository challengeHistoryRepository;


    public List<ChallengeHistory> getWeeklySummary(List<LocalDate> datesOfWeek, List<Long> oldestCharm) {

        return challengeHistoryRepository.getWeeklySummary(datesOfWeek, oldestCharm);
    }

    public MostEmotionResponse getMostEmotionsStatistics() {

        Long userId = SecurityUtil.getUserId();
        return challengeHistoryRepository.getMostEmotion(userId);

    }
}
