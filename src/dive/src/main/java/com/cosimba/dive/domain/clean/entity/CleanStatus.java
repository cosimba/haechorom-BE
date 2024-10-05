package com.cosimba.dive.domain.clean.entity;

public enum
CleanStatus {
    UNKNOWN("미분류"),
    CLEAN("청소완료"),
    IN_PROGRESS("청소중"),
    NOT_STARTED("미청소");

    private final String cleanStatus;

    CleanStatus(String cleanStatus) {
        this.cleanStatus = cleanStatus;
    }
}
