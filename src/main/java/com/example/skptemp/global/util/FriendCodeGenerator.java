package com.example.skptemp.global.util;

import java.security.SecureRandom;

public class FriendCodeGenerator {
    // 코드에 사용할 문자의 집합
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static final int CODE_LENGTH = 7;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomCode() {
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            codeBuilder.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return codeBuilder.toString();
    }
}
