package com.example.skptemp.domain.notification.repository;

import com.example.skptemp.domain.notification.dto.NotificationType;
import com.example.skptemp.domain.notification.entity.NotificationEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.skptemp.domain.notification.entity.QNotificationEntity.notificationEntity;

@Repository
@RequiredArgsConstructor
public class NotificationCustomRepositoryImpl implements NotificationCustomRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<NotificationEntity> getNotificationMessageList(Pageable pageable, NotificationType notificationType, Long userId) {

        List<NotificationEntity> fetch = queryFactory.selectFrom(notificationEntity)
                .where(notificationTypeEq(notificationType),
                        notificationEntity.userId.eq(userId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long count = queryFactory.select(notificationEntity.count())
                .from(notificationEntity)
                .where(notificationTypeEq(notificationType),
                        notificationEntity.userId.eq(userId))
                .fetchOne();
        return new PageImpl<>(fetch, pageable, count);

    }

    public BooleanExpression notificationTypeEq(NotificationType notificationType) {
        if (notificationType == null || notificationType.equals(NotificationType.ALL)) {
            return null;
        }
        return notificationEntity.notificationType.eq(notificationType);
    }
}

