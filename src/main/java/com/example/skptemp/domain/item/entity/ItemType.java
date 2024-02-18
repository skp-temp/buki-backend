package com.example.skptemp.domain.item.entity;

public enum ItemType {
    EQUIPMENT ("장착템"),
    CLOTHE("코스튬"),
    FASHION("패션소품");

    private String name;

    ItemType(String name){
        this.name = name;
    }
}
