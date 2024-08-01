package com.example.skptemp.domain.badge.service;

import com.example.skptemp.domain.badge.dto.UserBadgeResult;
import com.example.skptemp.domain.badge.entity.Badge;
import com.example.skptemp.domain.badge.entity.UserBadge;
import com.example.skptemp.domain.badge.repository.UserBadgeRepository;
import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.repository.CharmRepository;
import com.example.skptemp.global.constant.Category;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class BadgeServiceImpl implements BadgeService {
    private final UserBadgeRepository userBadgeRepository;
    private final CharmRepository charmRepository;

    @Transactional
    @Override
    public void completeBadge(Long userId, Badge badge) {
        validate(userId, badge);
        UserBadge userBadge = UserBadge.builder()
                .badge(badge)
                .userId(userId)
                .build();
        userBadgeRepository.save(userBadge);
    }

    @Transactional
    @Override
    public void refreshUserBadgeInfo(Long userId) {
        //TODO: 뱃지 획득 조건과 현재 사용자 달성 정보를 비교해서 뱃지 취득 처리

        //1. UserBadge 이용해서 뱃지 획득 정보를 저장해야 한다.

        //2. badge는 부적 완성 관련 / 응원 메시지 보내기 / 뽑기 실행 / 연속 접속 기록 / 친구 수

    }

    private void refreshCheerBadge(Category category, Long userId){ // 응원 관련 뱃지

    }
    private void refreshGachaBadge(Long userId){ // 뽑기 관련 뱃지

    }
    private void refreshFriendBadge(Long userId){ // 친구 관련 뱃지 갱신

    }
    private void refreshDayBadge(Long userId){ // 연속 접속 기록

    }

    private void refreshCharmBadge(Category category, Long userId){
        // 부적 유형 별로 완성 상태 확인해서 뱃지 획득 정보를 갱신한다.
        List<Charm> charmList = charmRepository.findByUserIdAndCategoryAndIsValidIsTrue(userId, category);


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
        return null;
    }
    private void validate(Long userId, Badge badge){
        if(userId == null || badge == null) throw new GlobalException(GlobalErrorCode.VALID_EXCEPTION);
    }
}
