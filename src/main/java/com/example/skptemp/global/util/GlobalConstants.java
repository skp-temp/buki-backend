package com.example.skptemp.global.util;

public class GlobalConstants {
    public static final Long TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 100L;    // 100 days
    public static final Long TEST_TOKEN_EXPIRATION_TIME = 1000 * 60 * 10L;          // 10 minutes
    public static final Long TEST_ACCOUNT_ID = 1L;                                  // 테스트 토큰 발급용 계정 ID
    public static final String JWT_USER_ID_KEY = "user_id";                         // jwt key user id 생성 및 조회 용도
    public static final String JWT_USER_ROLE = "role";                              // jwt key role 생성 및 조회 용도
}
