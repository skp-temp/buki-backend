package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.entity.ChallengeHistory;
import com.example.skptemp.domain.statistics.MostEmotionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChallengeHistoryRepository extends JpaRepository<ChallengeHistory, Long>, ChallengeHistoryCustomRepository {


    @Query(value = """
            SELECT count(*) as count , c.category,ch.emotion_type
            FROM test.challenge_history as ch
            INNER JOIN test.charm c on ch.charm_id = c.charm_id
            WHERE ch.user_id = :userId
            GROUP BY c.category,ch.emotion_type
            """, nativeQuery = true)
    MostEmotionResponse getMostEmotion(@Param("userId") Long userId);


}