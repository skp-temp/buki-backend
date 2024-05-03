package com.example.skptemp.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GlobalErrorCode {
    // 서버
    OTHER(HttpStatus.INTERNAL_SERVER_ERROR, "G100", "서버에 오류가 발생했습니다"),
    
    // 전체
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "G200", "허용되지 않은 메서드입니다"),
    VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "G300", "유효 하지 않은 요청입니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "G400", "허용되지 않은 사용자입니다"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "G500", "토큰이 만료되었습니다."),
    USER_CONFLICT(HttpStatus.CONFLICT, "G600", "이미 가입된 내역이 있습니다."),

    // 사용자 + 계정
    USER_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "G310", "유저가 유효하지 않습니다."),
    USER_DELETED_EXCEPTION(HttpStatus.BAD_REQUEST, "G311", "삭제된 계정입니다."),
    TEST_ACCOUNT_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "G312", "테스트용 계정 오류입니다."),

    // 아이템
    ITEM_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "G320", "아이템이 유효하지 않습니다."),
    ITEM_COUNT_EXCEPTION(HttpStatus.BAD_REQUEST, "G321", "아이템 개수가 유효하지 않습니다."),

    // 부적
    CHARM_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "G330", "부적이 유효하지 않습니다."),
    CHARM_TODAY_ALREADY_COMPLETE(HttpStatus.BAD_REQUEST, "G331", "오늘 이미 수행한 부적 입니다."),

    // 친구
    FRIEND_RELATIONSHIP_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "G340", "친구 관계가 유효하지 않습니다."),

    // 선물(??)
    USER_ITEM_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "G350", "유저 혹은 아이템 정보가 유효하지 않습니다."),

    // 뱃지
    BADGE_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "G360", "삭제된 뱃지입니다."),

    ;
    private final String code;
    private final String message;
    private final HttpStatus status;

    GlobalErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
