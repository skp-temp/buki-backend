package com.example.skptemp.domain.badge.service;

import com.example.skptemp.domain.badge.dto.UserBadgeResult;
import com.example.skptemp.domain.badge.entity.Badge;
import com.example.skptemp.domain.badge.entity.UserBadge;
import com.example.skptemp.domain.badge.repository.BadgeRepository;
import com.example.skptemp.domain.badge.repository.UserBadgeRepository;
import com.example.skptemp.global.constant.BadgeType;
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
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;

    @Transactional
    @Override
    public void completeBadge(Long userId, Long badgeId) {
        validate(userId, badgeId);
        UserBadge userBadge = UserBadge.builder()
                .badgeId(badgeId)
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

        //3.
    }

    private void refreshCheerBadge(Long userId){ // 응원 관련 뱃지

    }
    private void refreshGachaBadge(Long userId){ // 뽑기 관련 뱃지

    }
    private void refreshFriendBadge(Long userId){ // 친구 관련 뱃지 갱신

    }
    private void refreshDayBadge(Long userId){ // 연속 접속 기록

    }

    private void refreshCharmBadge(Category category){
        // 부적 유형 별로 완성 상태 확인해서 뱃지 획득 정보를 갱신한다.


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

    @Transactional
    @Override
    public void createBadge(String name, String description, String tip, BadgeType badgeType) {
        Badge badge = Badge.builder()
                .name(name)
                .description(description)
                .tip(tip)
                .badgeType(badgeType)
                .build();
        badgeRepository.save(badge);
    }
    @Override
    public List<Badge> getAllBadges() {
        return badgeRepository.findByIsValidIsTrue();
    }
    private void validate(Long userId, Long badgeId){
        if(userId == null || badgeId == null) throw new GlobalException(GlobalErrorCode.VALID_EXCEPTION);
        if(badgeRepository.findByIdAndIsValidIsTrue(badgeId).isEmpty()) throw new GlobalException("존재하지 않는 뱃지 정보입니다.", GlobalErrorCode.Badge_VALID_EXCEPTION);
    }
}
