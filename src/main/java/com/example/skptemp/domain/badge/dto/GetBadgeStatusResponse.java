package com.example.skptemp.domain.badge.dto;

import java.util.List;

public record GetBadgeStatusResponse(List<UserBadgeResult> userBadgeResultList) {
}
