package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.dto.ChallengeHistoryResult;
import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;
import com.example.skptemp.domain.charm.response.QCharmDetailResponse;
import com.example.skptemp.global.constant.EmotionType;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.skptemp.domain.charm.entity.QChallengeHistory.challengeHistory;
import static com.example.skptemp.domain.charm.entity.QCharm.charm;

@Repository
@RequiredArgsConstructor
public class QuerydslCharmRepositoryImpl implements QuerydslCharmRepository {


    private final JPAQueryFactory queryFactory;

    @Override
    public CharmDetailResponse getDetail(Long charmId, Long userId) {

        JPAQuery<Tuple> results = queryFactory.select(challengeHistory.id, challengeHistory.emotionType, challengeHistory.comment)
                .from(challengeHistory)
                .where(challengeHistory.charmId.eq(charmId), challengeHistory.userId.eq(userId))
                .fetchAll();
        CharmDetailResponse response = queryFactory.select(new QCharmDetailResponse(charm.category, charm.goal))
                .from(charm)
                .where(charm.id.eq(charmId))
                .fetchOne();

        List<ChallengeHistoryResult> challengeHistoryList = results.stream()
                .map(result -> {
                    return new ChallengeHistoryResult(result.get(1, Long.class), result.get(2, EmotionType.class), result.get(3, String.class));
                }).toList();

        response.setCount(challengeHistoryList.size());
        response.setChallengeHistoryResultList(challengeHistoryList);
        
        return response;

    }

    @Override
    public void settingUpdate(Long charmId, CharmSettingUpdateRequest request) {
        queryFactory.update(charm)
                .where(charm.id.eq(charmId))
                .set(charm.alarmOn, request.isAlarmOn())
                .set(charm.alarmTime, request.getAlarmTime())
                .set(charm.alarmDayType, request.getAlarmDayType())
                .execute();
    }

}
