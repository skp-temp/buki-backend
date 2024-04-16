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
        userRepository.save(User.createUser(LoginType.KAKAO, "1", "강1", "동훈",".."));
        userRepository.save(User.createUser(LoginType.KAKAO, "2", "강2", "동훈",".."));
        userRepository.save(User.createUser(LoginType.KAKAO, "3", "강3", "동훈",".."));
    }
}
