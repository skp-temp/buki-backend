package com.example.skptemp.global.constant;

public enum Category {
    HUSTLE("갓생"),
    HAPPY("행복"),
    EXERCISE("운동"),
    STUDY("공부"),
    MONEY("금전"),
    DIET("식습관"),
    BEAUTY("뷰티"),
    PET("반려동물");

    final String description;

    Category(String description){
        this.description = description;
    }
}
