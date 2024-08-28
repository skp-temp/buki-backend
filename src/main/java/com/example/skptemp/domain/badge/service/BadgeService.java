package com.example.skptemp.domain.badge.service;

import com.example.skptemp.domain.badge.dto.BadgeResult;
import com.example.skptemp.domain.badge.dto.UserBadgeResult;

import java.util.List;

public interface BadgeService {
    void refreshUserBadgeInfo(Long userId); // 사용자 뱃지 획득 정보 갱신
    List<UserBadgeResult> getUserBadgeInfo(Long userId); // 사용자가 획득한 뱃지 정보 제공
    List<BadgeResult> getAllBadges();
}
