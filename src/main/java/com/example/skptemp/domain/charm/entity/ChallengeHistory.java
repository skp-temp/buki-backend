package com.example.skptemp.domain.charm.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "ChallengeHistory", indexes = {
        @Index(name = "idx_challenge_history_user_id_charm_id", columnList = "user_id, charm_id")
})
public class ChallengeHistory {
    @Column(name = "history_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;


    @Column(name = "charm_id", nullable = false)
    private Long charmId;

    @Builder
    public ChallengeHistory(Long userId, Long charmId, LocalDate historyDate) {
        this.userId = userId;
        this.charmId = charmId;
        this.historyDate = historyDate;
    }

    private LocalDate historyDate;
}
