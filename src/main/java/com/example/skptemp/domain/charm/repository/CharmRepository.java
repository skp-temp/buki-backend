package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.statistics.StatisticsCategoryRankingResponse;
import com.example.skptemp.global.constant.AlarmDayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;
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

    List<Charm> findByAlarmTimeAndAlarmDayTypeAndAlarmOn(LocalDateTime alarmTime, AlarmDayType alarmDayType, Boolean alarmOn);

    List<Charm> findByAlarmOnTrueAndAlarmTimeAndAlarmDayTypeIn(LocalDateTime alarmTime, Collection<AlarmDayType> alarmDayTypes);
}