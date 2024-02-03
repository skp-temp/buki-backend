package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.request.CharmSettingUpdateRequest;
import com.example.skptemp.domain.charm.response.CharmDetailResponse;
import com.example.skptemp.domain.charm.response.QCharmDetailResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.skptemp.domain.category.entity.QCategory.category;
import static com.example.skptemp.domain.charm.entity.QChallengeHistory.challengeHistory;
import static com.example.skptemp.domain.charm.entity.QCharm.charm;

@Repository
@RequiredArgsConstructor
public class QuerydslCharmRepositoryImpl implements QuerydslCharmRepository {


    private final JPAQueryFactory queryFactory;

    @Override
    public CharmDetailResponse getDetail(Long charmId, Long userId) {

        long count = queryFactory.select(challengeHistory.count())
                .from(challengeHistory)
                .where(challengeHistory.charmId.eq(charmId), challengeHistory.userId.eq(userId))
                .fetchFirst();
        CharmDetailResponse response = queryFactory.select(new QCharmDetailResponse(category.name, charm.goal, charm.characterId))
                .from(charm)
                .innerJoin(category).on(charm.categoryId.eq(category.id))
                .where(charm.id.eq(charmId))
                .fetchOne();

        response.setCount(count);

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
