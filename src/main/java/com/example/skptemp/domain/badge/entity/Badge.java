package com.example.skptemp.domain.badge.entity;

import com.example.skptemp.global.constant.BadgeType;

public enum Badge {
    CheerLeader(0,"치어리더", "응원메시지 10회 보내기", "Tip. 응원 메시지를 10번 이상 보내보세요.", BadgeType.CHEER),
    GachaKing(1,"뽑기왕", "뽑기 5회 실행", "Tip. 뽑기 5회 실행", BadgeType.GACHA),
    GageunSang(2,"개근상", "21일 연속 접속", "Iip. 연속 21일 이상 접속해보세요.", BadgeType.DAY),
    MyPartener(3,"내동료가돼라", "친구 10명 모으기", "Tip. 친구를 10명 이상 만들어보세요", BadgeType.FRIEND),
    GodSang(4,"갓생러", "갓생 부적 3회 완성", "Tip. 갓생 부적을 3개 완성해보세요.", BadgeType.CHARM),
    KangHyungWook(5,"강형욱", "반려 부적 3회 완성", "Tip. 반려 부적을 3개 완성해보세요.", BadgeType.CHARM),
    NationalAthelete(6,"국가대표", "운동 부적 3회 완성", "Tip. 운동 부적을 3개 완성해보세요.", BadgeType.CHARM),
    Doctor(7,"척척박사", "공부 부적 3회 완성", "Tip. 공부 부적을 3개 완성해보세요.", BadgeType.CHARM),
    RichMan(8,"건물주", "금전 부적 3회 완성", "Tip. 금전 부적을 3개 완성해보세요.", BadgeType.CHARM),
    BeauTuber(9,"뷰튜버", "뷰티 부적 3회 완성", "Tip. 뷰티 부적을 3개 완성해보세요.", BadgeType.CHARM),
    LoveMySelf(10,"럽마이셀프", "식습관 부적 3회 완성", "Tip. 식습관 부적을 3개 완성해보세요.", BadgeType.CHARM),
    Happiness(11,"행복전도사", "행복 부적 3회 완성", "Tip. 행복 부적을 3개 완성해보세요.", BadgeType.CHARM)
    ;
    int id;
    String name;
    String description;
    String tip;
    BadgeType badgeType;

    Badge(int id, String name, String description, String tip, BadgeType badgeType){
        this.id = id;
        this.name = name;
        this.description = description;
        this.tip = tip;
        this.badgeType = badgeType;
    }

}
