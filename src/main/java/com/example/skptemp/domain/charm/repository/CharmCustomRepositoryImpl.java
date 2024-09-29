package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.dto.*;
import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;
import com.example.skptemp.domain.charm.response.QCharmDetailResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.skptemp.domain.charm.entity.QChallengeHistory.challengeHistory;
import static com.example.skptemp.domain.charm.entity.QCharm.charm;
import static com.example.skptemp.domain.charm.entity.QCharmItem.charmItem;


@Repository
@RequiredArgsConstructor
public class CharmCustomRepositoryImpl implements CharmCustomRepository {


    private final JPAQueryFactory queryFactory;


    @Override
    public CharmDetailResponse getDetail(Long charmId, Long userId) {

        List<ChallengeHistoryResult> challengeHistoryList = queryFactory.select(new QChallengeHistoryResult(challengeHistory.id, challengeHistory.emotionType, challengeHistory.comment, challengeHistory.historyDate))
                .from(challengeHistory)
                .where(challengeHistory.charmId.eq(charmId), challengeHistory.userId.eq(userId))
                .fetch();
        CharmDetailResponse response = queryFactory.select(new QCharmDetailResponse(charm.category, charm.goal))
                .from(charm)
                .where(charm.id.eq(charmId))
                .fetchOne();


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

    @Override
    public List<Charm> getOldestCharm(Long userId, int size) {
        return queryFactory.selectFrom(charm)
                .orderBy(charm.createdAt.asc())
                .where(charm.userId.eq(userId))
                .limit(size)
                .fetch();
    }

    @Override
    public List<CharmListResponse> getCharmList(Long userId, CharmSort sort) {


        OrderSpecifier<?> order = charm.createdAt.asc();
        switch (sort) {
            case CREATE -> order = charm.createdAt.asc();
            case RECENT -> order = charm.completeAt.desc();
            case CATEGORY -> order = charm.category.asc();
        }


        return queryFactory
                .select(new QCharmListResponse(
                        challengeHistory.id.count(),
                        charm.id,
                        charm.id.count().eq(21L),
                        charm.category,
                        charm.goal))
                .orderBy(order)
                .from(charm)
                .leftJoin(challengeHistory).on(challengeHistory.charmId.eq(charm.id))
                .innerJoin(charmItem).on(charmItem.charmId.eq(charm.id))
                .groupBy(charm.id)
                .fetch();
    }
}
