package com.cosimba.dive.domain.josa.entity;

public enum JosaStatus {
    WAITING("대기"),
    IN_PROGRESS("진행중"),
    FINISHED("완료");

    private final String status;

    JosaStatus(String status) {
        this.status = status;
    }
}
