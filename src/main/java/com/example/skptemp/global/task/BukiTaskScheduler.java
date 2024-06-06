package com.example.skptemp.global.task;

import com.example.skptemp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BukiTaskScheduler {
    private final UserRepository userRepository;
    private static final String everyMidNightCronExpression =
            "0 0 0 * * ?";

    // 매일 자정에 gacha status를 false로 세팅한다.
    @Scheduled(cron = everyMidNightCronExpression)
    public void refreshGachaStatusTask(){
        userRepository.updateAllGachaStatusToFalse();
        log.info("refresh all user's gacha status to false completed");
    }
}
