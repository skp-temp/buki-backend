package com.example.skptemp.domain.charm.dto;

import com.example.skptemp.global.constant.EmotionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CompleteTodayRequest {

    @NotNull
    private EmotionType emotionType;
    @Positive
    private Long charmId;
    @NotBlank
    private String comment;
}
