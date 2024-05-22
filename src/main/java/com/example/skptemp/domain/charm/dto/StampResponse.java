package com.example.skptemp.domain.charm.dto;

import com.example.skptemp.global.constant.EmotionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StampResponse {

    private String historyDate;

    private EmotionType emotionType;
}
