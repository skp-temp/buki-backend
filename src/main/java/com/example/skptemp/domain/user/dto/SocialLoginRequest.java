package com.example.skptemp.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SocialLoginRequest {
    @Schema(description = "연동 로그인 후 발급된 코드", example = "asdf")
    private String code;
}
