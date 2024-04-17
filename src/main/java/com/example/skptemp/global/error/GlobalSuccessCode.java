package com.example.skptemp.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GlobalSuccessCode {
    SUCCESS(HttpStatus.OK, "G000", "요청에 성공했습니다."),
    CREATED(HttpStatus.CREATED, "G001", "데이터 생성 성공"),
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;

    GlobalSuccessCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
