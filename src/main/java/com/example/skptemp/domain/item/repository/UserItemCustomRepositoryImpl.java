package com.example.skptemp.domain.item.repository;

import com.example.skptemp.domain.item.dto.CheerItemResponse;
import com.example.skptemp.domain.item.dto.QCheerItemResponse;
import com.example.skptemp.domain.item.entity.UserItem;
import com.example.skptemp.global.constant.Category;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.skptemp.domain.item.entity.QItem.item;
import static com.example.skptemp.domain.item.entity.QUserItem.userItem;

@Repository
@RequiredArgsConstructor
public class UserItemCustomRepositoryImpl implements UserItemCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CheerItemResponse> getCheerItemList(Long userId, Category category) {

        return jpaQueryFactory.select(
                        new QCheerItemResponse(
                                userItem.count,
                                userItem.userId,
                                item.itemName,
                                item.category,
                                item.itemType))
                .from(userItem)
                .fetchJoin()
                .innerJoin(item)
                .on(item.id.eq(userItem.item.id))
                .where(userItem.userId.eq(userId), item.category.eq(category), userItem.count.gt(0))
                .fetch();
    }

    @Override
    public List<UserItem> getUserItemList(Long userId) {

        return jpaQueryFactory.selectFrom(userItem)
                .join(userItem.item,item)
                .fetchJoin()
                .where(userItem.userId.eq(userId))
                .fetch();
    }
}
