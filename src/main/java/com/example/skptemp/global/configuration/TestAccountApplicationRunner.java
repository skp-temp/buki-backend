package com.example.skptemp.global.configuration;

import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.repository.UserRepository;
import com.example.skptemp.global.constant.LoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestAccountApplicationRunner implements ApplicationRunner {
    private final UserRepository userRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user1 = userRepository.save(User.createUser(LoginType.KAKAO, "1", "강1", "동훈"));
        User user2 = userRepository.save(User.createUser(LoginType.KAKAO, "2", "강2", "동훈"));
    }
}
