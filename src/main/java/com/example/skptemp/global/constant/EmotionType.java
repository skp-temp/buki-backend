package com.example.skptemp.global.constant;

public enum EmotionType {
    LOVE("매우 기쁨"),
    JOY("즐거움"),
    NORMAL("보통"),
    SAD("슬픔"),
    ANGRY("분노");

    final String name;

    EmotionType(String name){
        this.name = name;
    }
    public static EmotionType get(int index){
        return switch (index) {
            case 0 -> LOVE;
            case 1 -> JOY;
            case 2 -> NORMAL;
            case 3 -> SAD;
            case 4 -> ANGRY;
            default -> NORMAL;
        };
    }
}
