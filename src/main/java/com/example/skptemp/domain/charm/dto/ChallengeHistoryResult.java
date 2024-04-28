package com.example.skptemp.domain.charm.dto;

import com.example.skptemp.global.constant.EmotionType;

public record ChallengeHistoryResult(Long id, EmotionType emotionType, String comment) {
}
