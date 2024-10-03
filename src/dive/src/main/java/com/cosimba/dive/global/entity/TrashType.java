package com.cosimba.dive.global.entity;

public enum TrashType {
    VOID("미분류"),
    FISHING_NET("폐어구류"),
    BUOY("부표류"),
    DAILY_TRASH("생활쓰레기류"),
    LARGE_TRASH("대형 투기쓰레기류"),
    WOOD("초목류");

    private final String trashType;

    TrashType(String trashType) {
        this.trashType = trashType;
    }
}
