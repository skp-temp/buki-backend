package com.example.skptemp.domain.cheer.repository;

import com.example.skptemp.domain.cheer.dto.CheerCountResponse;
import com.example.skptemp.domain.cheer.dto.QCheerCountResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.skptemp.domain.cheer.entity.QCheer.cheer;
import static com.example.skptemp.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class CheerCustomRepositoryImpl implements CheerCustomRepository {

    public static final String COUNT = "count";
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CheerCountResponse> getCheerCount(Long userId) {


        return queryFactory.select(new QCheerCountResponse(cheer.count().as(COUNT), cheer.toUser, user.lastName.concat(user.firstName).as("name")))
                .from(cheer)
                .where(cheer.fromUser.eq(userId))
                .innerJoin(user).on(cheer.toUser.eq(user.id))
                .groupBy(cheer.toUser)
                .orderBy(cheer.count().desc())
                .limit(3)
                .fetch();

    }

    @Override
    public List<CheerCountResponse> getCheeredCount(Long userId) {


        return queryFactory.select(new QCheerCountResponse(cheer.count().as(COUNT), cheer.fromUser, user.lastName.concat(user.firstName).as("name")))
                .from(cheer)
                .where(cheer.toUser.eq(userId))
                .groupBy(cheer.fromUser)
                .innerJoin(user).on(cheer.fromUser.eq(user.id))
                .orderBy(cheer.count().desc())
                .limit(3)
                .fetch();
    }
}
