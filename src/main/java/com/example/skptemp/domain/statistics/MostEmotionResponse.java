package com.example.skptemp.domain.statistics;


import com.example.skptemp.global.constant.Category;
import com.example.skptemp.global.constant.EmotionType;

public interface MostEmotionResponse {

    Long getCount();


    EmotionType getEmotionType();

    Category getCategory();


}
