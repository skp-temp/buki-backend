package com.example.skptemp.global.configuration;

import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.repository.CharmRepository;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.repository.UserRepository;
import com.example.skptemp.global.constant.AlarmDayType;
import com.example.skptemp.global.constant.Category;
import com.example.skptemp.global.constant.LoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestAccountApplicationRunner implements ApplicationRunner {
    private final UserRepository userRepository;
    private final CharmRepository charmRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user1 = userRepository.save(User.createUser(LoginType.KAKAO, "1", "강1", "동훈", ".."));
        User user2 = userRepository.save(User.createUser(LoginType.KAKAO, "2", "강2", "동훈", ".."));
        userRepository.save(User.createUser(LoginType.KAKAO, "2", "태규1", "최", ".."));
        userRepository.save(User.createUser(LoginType.KAKAO, "2", "태규2", "최", ".."));
        userRepository.save(User.createUser(LoginType.KAKAO, "2", "태규3", "최", ".."));

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
                .category(Category.BEAUTY)
                .goal("집에 가자2")
                .alarmOn(false)
                .alarmDayType(AlarmDayType.EVERYDAY)
                .alarmTime(LocalDateTime.now())
                .build()
        );
    }
}
