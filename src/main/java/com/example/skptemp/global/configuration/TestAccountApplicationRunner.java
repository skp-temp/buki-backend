package com.example.skptemp.global.configuration;

import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.repository.UserRepository;
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
        User user1 = userRepository.save(User.createUser(1L));
        user1.changeName("강", "동훈");
        User user2 = userRepository.save(User.createUser(2L));
        user2.changeName("강2", "동훈");
    }
}
