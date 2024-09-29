package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.dto.CharmItemDetailResponse;
import com.example.skptemp.domain.charm.dto.QCharmItemDetailResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.skptemp.domain.charm.entity.QCharmItem.charmItem;
import static com.example.skptemp.domain.item.entity.QItem.item;

@RequiredArgsConstructor
@Repository
public class CharmItemCustomRepositoryImpl implements CharmItemCustomRepository {

    private final JPAQueryFactory queryFactory;



    @Override
    public List<CharmItemDetailResponse> getCharmItemList(List<Long> charmIdList) {

        return queryFactory.select(
                        new QCharmItemDetailResponse(
                                charmItem.itemId,
                                charmItem.charmId,
                                item.itemType,
                                item.itemName,
                                item.category
                        )
                ).from(charmItem)
                .innerJoin(item)
                .on(charmItem.itemId.eq(item.id))
                .where(charmItem.charmId.in(charmIdList))
                .fetch();
    }
}
