package com.example.skptemp.domain.badge.service;

import com.example.skptemp.domain.badge.dto.*;
import com.example.skptemp.domain.badge.entity.*;
import com.example.skptemp.domain.badge.repository.UserBadgeRepository;
import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.repository.CharmRepository;
import com.example.skptemp.domain.cheer.entity.Cheer;
import com.example.skptemp.domain.cheer.repository.CheerRepository;
import com.example.skptemp.domain.user.entity.FriendRelationship;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.repository.FriendRelationshipRepository;
import com.example.skptemp.domain.user.repository.UserRepository;
import com.example.skptemp.global.constant.BukiConstant;
import com.example.skptemp.global.constant.Category;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class BadgeServiceImpl implements BadgeService {
    private final UserBadgeRepository userBadgeRepository;
    private final CharmRepository charmRepository;
    private final CheerRepository cheerRepository;
    private final UserRepository userRepository;
    private final FriendRelationshipRepository friendRepository;

    @Transactional
    @Override
    public void refreshUserBadgeInfo(Long userId) {
        //1. UserBadge 이용해서 뱃지 획득 정보를 저장해야 한다.
        //2. badge는 부적 완성 관련 / 응원 메시지 보내기 / 뽑기 실행 / 연속 접속 기록 / 친구 수
        refreshCheerBadge(userId);
        refreshFriendBadge(userId);
        refreshDayBadge(userId);
        refreshGachaBadge(userId);
        refreshDayBadge(userId);
        refreshAllCharmBadge(userId);
    }
    private void refreshCheerBadge(Long userId){ // 응원 관련 뱃지
        List<Cheer> cheerList = cheerRepository.findByFromUser(userId);
        
        if(cheerList.size() > BukiConstant.CHEER_UP_BADGE_COUNT)
            completeBadge(userId, Badge.CheerLeader);
    }

    private void refreshGachaBadge(Long userId){ // 뽑기 관련 뱃지
        Optional<User> userOpt = userRepository.findById(userId);
        User user = userOpt.get();

        if(user.getGachaCount() >= BukiConstant.GACHA_BADGE_COUNT)
            completeBadge(userId, Badge.GachaKing);
    }
    private void refreshFriendBadge(Long userId){ // 친구 관련 뱃지 갱신
        List<FriendRelationship> list = friendRepository.findByUserA(userId);

        if(list.size() >= BukiConstant.FRIEND_BADGE_COUNT)
            completeBadge(userId, Badge.MyPartener);
    }
    private void refreshDayBadge(Long userId){ // 연속 접속 기록
        // TODO: 유저쪽 코드 수정 선행이 필요
        // 1. 매일 접속 이력이 기록 되어야 한다.
        // 2. 접속 이력 기록을 위해서는 접속 시 고정적으로 호출하는 API 필요
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

        giveCharmBadge(userId, category);
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
    public List<BadgeResult> getAllBadges() {
        return Arrays.stream(Badge.values())
                .map(badge -> new BadgeResult(badge.name(), badge.getId(), badge.getName(), badge.getDescription(), badge.getTip()))
                .toList();
    }

    private void giveCharmBadge(Long userId, Category category){
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
