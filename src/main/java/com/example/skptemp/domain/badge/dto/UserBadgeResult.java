package com.example.skptemp.domain.badge.dto;

import com.example.skptemp.domain.badge.entity.UserBadge;
import lombok.Builder;

import java.time.LocalDate;

public record UserBadgeResult(Long id, Long badgeId, Long userId, boolean isValid, LocalDate completedAt) {
    @Builder
    public UserBadgeResult(UserBadge userBadge){
        this(userBadge.getId(),
                userBadge.getBadgeId(),
                userBadge.getUserId(),
                userBadge.isValid(),
                userBadge.getCompletedAt());
    }
}
