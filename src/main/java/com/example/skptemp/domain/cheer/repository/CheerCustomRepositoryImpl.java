package com.example.skptemp.domain.cheer.repository;

import com.example.skptemp.domain.cheer.dto.CheerCountResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.skptemp.domain.cheer.QCheer.cheer;


@RequiredArgsConstructor
public class CheerCustomRepositoryImpl implements CheerCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CheerCountResponse> getCheerCount(boolean isDesc, Long userId) {

        OrderSpecifier<Long> order = isDesc ? cheer.as("count").count().desc() : cheer.as("count").count().asc();



        return queryFactory.select(new QCheerCountResponse(cheer.count().as("count"),cheer.from))
                .from(cheer)
                .where(cheer.to.eq(userId))
                .groupBy(cheer.from)
                .orderBy(order)
                .limit(3)
                .fetch();

    }
}
