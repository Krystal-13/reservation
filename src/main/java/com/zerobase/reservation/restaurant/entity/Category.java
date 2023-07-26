package com.zerobase.reservation.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {
    KoreanFood("한식"),
    JapaneseFood("일식"),
    ChineseFood("중식"),
    WesternFood("양식"),
    AsianFood("아시아식"),
    FastFood("패스트푸드"),
    FamilyRestaurant("패밀리레스토랑"),
    CafeOrBakery("카페/베이커리");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public static String getCategory(String name) {
        for (var c : Category.values()) {
            if (c.name.equals(name)) {
                return c.name;
            }
        }
        return null;
    }

}
