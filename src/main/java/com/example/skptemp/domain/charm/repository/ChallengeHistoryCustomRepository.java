package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.entity.ChallengeHistory;

import java.time.LocalDate;
import java.util.List;

public interface ChallengeHistoryCustomRepository {


    List<ChallengeHistory> getWeeklySummary(List<LocalDate> dates, List<Long> oldestCharm);
}
