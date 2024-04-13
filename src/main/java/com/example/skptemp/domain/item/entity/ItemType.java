package com.example.skptemp.domain.item.entity;

public enum ItemType {
    EQUIPMENT ("장착템", 1),
    CLOTHE("코스튬", 2),
    FASHION("패션 소품", 3);

    private String name;
    private int code;

    ItemType(String name, int code){
        this.name = name;
        this.code = code;
    }
}
