package com.example.skptemp.domain.badge.service;

import com.example.skptemp.domain.badge.dto.UserBadgeResult;
import com.example.skptemp.domain.badge.entity.Badge;
import com.example.skptemp.domain.badge.entity.UserBadge;
import com.example.skptemp.domain.badge.repository.UserBadgeRepository;
import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.repository.CharmRepository;
import com.example.skptemp.global.constant.BukiConstant;
import com.example.skptemp.global.constant.Category;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class BadgeServiceImpl implements BadgeService {
    private final UserBadgeRepository userBadgeRepository;
    private final CharmRepository charmRepository;

    @Transactional
    @Override
    public void refreshUserBadgeInfo(Long userId) {
        //1. UserBadge 이용해서 뱃지 획득 정보를 저장해야 한다.
        //2. badge는 부적 완성 관련 / 응원 메시지 보내기 / 뽑기 실행 / 연속 접속 기록 / 친구 수
        refreshFriendBadge(userId);
        refreshDayBadge(userId);
        refreshGachaBadge(userId);
        refreshDayBadge(userId);
        refreshAllCharmBadge(userId);
    }

    private void refreshCheerBadge(Long userId){ // 응원 관련 뱃지

    }
    private void refreshGachaBadge(Long userId){ // 뽑기 관련 뱃지

    }
    private void refreshFriendBadge(Long userId){ // 친구 관련 뱃지 갱신

    }
    private void refreshDayBadge(Long userId){ // 연속 접속 기록

    }

    private void refreshAllCharmBadge(Long userId){
        for (var category : Category.values()){
            refreshCharmBadge(category, userId);
        }
    }
    private void refreshCharmBadge(Category category, Long userId){
        // 부적 유형 별로 완성 상태 확인해서 뱃지 획득 정보를 갱신한다.
        List<Charm> charmList = charmRepository.findByUserIdAndCategoryAndIsValidIsTrue(userId, category);
        int completedCharmCnt = 0;

        for(var charm: charmList){
            if(charm.getCharmLevel() == BukiConstant.CHARM_FINAL_COMPLETE_DAY)
                completedCharmCnt++;
        }

        if(completedCharmCnt < BukiConstant.CHARM_BADGE_COMPLETE_COUNT)
            return;

        giveBadge(userId, category);
    }


    @Override
    public List<UserBadgeResult> getUserBadgeInfo(Long userId) {
        List<UserBadge> userBadgeList =
                userBadgeRepository.findByUserIdAndIsValidIsTrue(userId);

        return userBadgeList.stream()
                .map(userBadge ->
                        UserBadgeResult.builder()
                                .userBadge(userBadge)
                                .build()
                ).toList();
    }

    @Override
    public List<Badge> getAllBadges() {
        return Arrays.stream(Badge.values())
                .toList();
    }
    private void giveBadge(Long userId, Category category){
        switch(category){
            case EXERCISE -> {
                completeBadge(userId, Badge.NationalAthelete);
            }
            case PET -> {
                completeBadge(userId, Badge.KangHyungWook);
            }
            case DIET -> {
                completeBadge(userId, Badge.LoveMySelf);
            }
            case HAPPY -> {
                completeBadge(userId, Badge.Happiness);
            }
            case MONEY -> {
                completeBadge(userId, Badge.RichMan);
            }
            case STUDY -> {
                completeBadge(userId, Badge.Doctor);
            }
            case BEAUTY -> {
                completeBadge(userId, Badge.BeauTuber);
            }
            case HUSTLE -> {
                completeBadge(userId, Badge.GodSang);
            }
        }
    }

    private void completeBadge(Long userId, Badge badge) {
        validate(userId, badge);
        UserBadge userBadge = UserBadge.builder()
                .badge(badge)
                .userId(userId)
                .build();
        userBadgeRepository.save(userBadge);
    }
    private void validate(Long userId, Badge badge) {
        if (userId == null || badge == null) throw new GlobalException(GlobalErrorCode.VALID_EXCEPTION);
    }
}
