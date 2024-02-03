package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.entity.ChallengeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeHistoryRepository extends JpaRepository<ChallengeHistory, Long> {
}