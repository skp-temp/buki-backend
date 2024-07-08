package com.example.skptemp.domain.statistics;


import com.example.skptemp.domain.charm.entity.ChallengeHistory;
import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.service.ChallengeHistoryService;
import com.example.skptemp.domain.charm.service.CharmService;
import com.example.skptemp.domain.cheer.dto.CheerCountResponse;
import com.example.skptemp.domain.cheer.service.CheerService;
import com.example.skptemp.domain.user.dto.UserResponse;
import com.example.skptemp.domain.user.service.FriendRelationshipService;
import com.example.skptemp.domain.user.service.UserService;
import com.example.skptemp.global.common.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticsFacadeService {

    private final FriendRelationshipService friendRelationshipService;

    private final CharmService charmService;

    private final UserService userService;

    private final ChallengeHistoryService challengeHistoryService;

    private final CheerService cheerService;


    // 디자인 컴포 1번
    public StatisticsUserResponse getUserStatistics() {
        Long userId = SecurityUtil.getUserId();
        UserResponse user = userService.findByUserId(userId);
        LocalDateTime createdAt = user.getCreatedAt();
        long days = (Duration.between(createdAt, LocalDateTime.now()).getSeconds()) / 86400;
        return new StatisticsUserResponse(days);
    }

    // 디자인 컴포 2번
    public StatisticsWeeklyResponse weeklySummary(StatisticsWeeklyRequest request) {
        Integer year = request.getYear();
        Integer month = request.getMonth();
        Integer week = request.getWeek();

        List<LocalDate> datesOfWeek = getDatesOfWeek(year, month, week);

        List<Charm> oldestCharm = charmService.getOldestCharm(8);
        List<Long> oldestCharmIdList = oldestCharm.stream().map(Charm::getId).toList();

        List<ChallengeHistory> weeklySummary = challengeHistoryService.getWeeklySummary(datesOfWeek, oldestCharmIdList);
        List<StatisticsWeeklyResponse.StatisticsWeeklyItem> itemList = new ArrayList<>();
        for (Charm charm : oldestCharm) {
            List<Boolean> challegedList = new ArrayList<>();
            for (LocalDate date : datesOfWeek) {
                Optional<ChallengeHistory> first = weeklySummary.stream().filter(challengeHistory -> challengeHistory.getHistoryDate().equals(date) && challengeHistory.getCharmId().equals(charm.getId())).findFirst();
                challegedList.add(first.isPresent());
            }
            itemList.add(new StatisticsWeeklyResponse.StatisticsWeeklyItem(charm.getGoal(), challegedList));
        }

        return new StatisticsWeeklyResponse(itemList);

    }

    // 디자인 컴포 3번
    public StatisticsCategoryRankingResponse getCategoryRanking() {
        return charmService.getCategoryRanking();
    }

    // 디자인 컴포 4번
    public MostEmotionResponse getMostEmotions() {

        return challengeHistoryService.getMostEmotionsStatistics();

    }

    // 디자인 컴포 5번
    public List<CheerCountResponse> getCheeringFriends() {
        return cheerService.getCheeringFriends();
    }

    public List<CheerCountResponse> getCheeredFriends() {

        return cheerService.getCheeredFriends();
    }

    private List<LocalDate> getDatesOfWeek(int year, int month, int week) {
        List<LocalDate> datesOfWeek = new ArrayList<>();

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate startOfWeek = firstDayOfMonth.with(DayOfWeek.SUNDAY);
        startOfWeek = startOfWeek.minusWeeks(1L).plusWeeks(week - 1);
        firstDayOfMonth.plusWeeks(week);
        for (int i = 0; i < 7; i++) {
            LocalDate date = startOfWeek.plusDays(i);
            datesOfWeek.add(date);
        }
        return datesOfWeek;
    }
}
