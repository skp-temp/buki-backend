package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.statistics.StatisticsCategoryRankingResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CharmRepository extends JpaRepository<Charm, Long>, CharmCustomRepository {

    Optional<Charm> findByIdAndIsValidIsTrue(Long id);

    @Query("""
            SELECT charm.category as category,count(charm.category) as count FROM Charm charm
            WHERE charm.userId = :userId
            GROUP BY charm.category
            ORDER BY count desc
            """)
    List<StatisticsCategoryRankingResponse> getCategoryRanking(Long userId);
}