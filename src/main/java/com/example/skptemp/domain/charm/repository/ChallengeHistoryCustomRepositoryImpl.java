package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.entity.ChallengeHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.example.skptemp.domain.charm.entity.QChallengeHistory.challengeHistory;

@RequiredArgsConstructor
@Repository
public class ChallengeHistoryCustomRepositoryImpl implements ChallengeHistoryCustomRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<ChallengeHistory> getWeeklySummary(List<LocalDate> dates, List<Long> oldestCharm) {


        return queryFactory.selectFrom(challengeHistory)
                .where(challengeHistory.charmId.in(oldestCharm),
                        challengeHistory.historyDate.in(dates))
                .fetch();
    }


}
