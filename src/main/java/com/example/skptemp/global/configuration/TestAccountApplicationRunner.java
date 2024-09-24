package com.example.skptemp.global.configuration;

import com.example.skptemp.domain.badge.entity.Badge;
import com.example.skptemp.domain.badge.entity.UserBadge;
import com.example.skptemp.domain.badge.repository.UserBadgeRepository;
import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.entity.CharmItem;
import com.example.skptemp.domain.charm.repository.CharmRepository;
import com.example.skptemp.domain.item.entity.Item;
import com.example.skptemp.domain.item.entity.ItemType;
import com.example.skptemp.domain.item.entity.UserItem;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.repository.UserRepository;
import com.example.skptemp.global.constant.AlarmDayType;
import com.example.skptemp.global.constant.Category;
import com.example.skptemp.global.constant.LoginType;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestAccountApplicationRunner implements ApplicationRunner {
    private final UserRepository userRepository;
    private final CharmRepository charmRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        User user1 = userRepository.save(User.createUser(LoginType.KAKAO, "1", "강1", "동훈", ".."));
        User user2 = userRepository.save(User.createUser(LoginType.KAKAO, "2", "강2", "동훈", ".."));
        User user3 = userRepository.save(User.createUser(LoginType.KAKAO, "2", "태규1", "최", ".."));
        User user4 = userRepository.save(User.createUser(LoginType.KAKAO, "2", "태규2", "최", ".."));
        User user5 = userRepository.save(User.createUser(LoginType.KAKAO, "2", "태규3", "최", ".."));

        Charm charm1 = charmRepository.save(Charm.builder()
                .userId(user1.getId())
                .category(Category.BEAUTY)
                .goal("집에 가자")
                .alarmOn(true)
                .alarmDayType(AlarmDayType.EVERYDAY)
                .alarmTime(LocalDateTime.now())
                .build()
        );
        Charm charm2 = charmRepository.save(Charm.builder()
                .userId(user1.getId())
                .category(Category.DIET)
                .goal("다이어트 부적")
                .alarmOn(false)
                .alarmDayType(AlarmDayType.EVERYDAY)
                .alarmTime(LocalDateTime.now())
                .build()
        );
        createTestUserBadgeData(user3.getId());

        // 테스트 아이템

        Item item1 = Item.create(Category.DIET, "다이어트 아이템 1", ItemType.CLOTHE);
        Item item2 = Item.create(Category.DIET, "다이어트 아이템 2", ItemType.EQUIPMENT);
        Item item3 = Item.create(Category.DIET, "다이어트 아이템 3", ItemType.FASHION);
        Item item4 = Item.create(Category.EXERCISE, "운동 아이템 1", ItemType.CLOTHE);


        entityManager.persist(item1);
        entityManager.persist(item2);
        entityManager.persist(item3);
        entityManager.persist(item4);

        UserItem userItem1 = UserItem.create(user5.getId(), item1);
        userItem1.addItem(10);
        UserItem userItem2 = UserItem.create(user5.getId(), item2);
        userItem2.addItem(10);
        UserItem userItem3 = UserItem.create(user5.getId(), item3);
        userItem3.addItem(10);
        UserItem userItem4 = UserItem.create(user5.getId(), item4);
        userItem4.addItem(10);

        entityManager.persist(userItem1);
        entityManager.persist(userItem2);
        entityManager.persist(userItem3);
        entityManager.persist(userItem4);

        CharmItem charmItem = new CharmItem(charm2.getId(), item1.getId());
        CharmItem charmItem1 = new CharmItem(charm2.getId(), item2.getId());
        CharmItem charmItem2 = new CharmItem(charm2.getId(), item3.getId());

        entityManager.persist(charmItem);
        entityManager.persist(charmItem1);
        entityManager.persist(charmItem2);

    }

    private void createTestUserBadgeData(Long userId) {
        userBadgeRepository.save(UserBadge.builder()
                .userId(userId)
                .badge(Badge.Happiness)
                .build());
        userBadgeRepository.save(UserBadge.builder()
                .userId(userId)
                .badge(Badge.GachaKing)
                .build());
    }

}
