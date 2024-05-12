package com.example.skptemp.domain.cheer;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.skptemp.domain.cheer.QCheer.cheer;
import static com.example.skptemp.domain.user.entity.QUser.user;


@RequiredArgsConstructor
public class CheerCustomRepositoryImpl implements CheerCustomRepository {

    public static final String COUNT = "count";
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CheerCountResponse> getCheerCount(Long userId) {


        return queryFactory.select(new QCheerCountResponse(cheer.count().as(COUNT), cheer.to_user, user.lastName.concat(user.firstName).as("name")))
                .from(cheer)
                .where(cheer.from_user.eq(userId))
                .innerJoin(user).on(cheer.to_user.eq(user.id))
                .groupBy(cheer.to_user)
                .orderBy(cheer.count().desc())
                .limit(3)
                .fetch();

    }

    @Override
    public List<CheerCountResponse> getCheeredCount(Long userId) {


        return queryFactory.select(new QCheerCountResponse(cheer.count().as(COUNT), cheer.from_user, user.lastName.concat(user.firstName).as("name")))
                .from(cheer)
                .where(cheer.to_user.eq(userId))
                .groupBy(cheer.from_user)
                .innerJoin(user).on(cheer.from_user.eq(user.id))
                .orderBy(cheer.count().desc())
                .limit(3)
                .fetch();
    }
}
