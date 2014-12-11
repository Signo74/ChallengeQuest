package com.swinginpenguin.vmarinov.challengequest.model.base;

/**
 * Created by vmarinov on 11/7/2014.
 */
public enum ErrorCodes {

    DB_ERROR(1),
    GENERAL_ERROR(2),
    ERROR_OK(0);

    private long errorCode;

    ErrorCodes(long value) {
        this.errorCode = value;
    }

    public long getErrorCode() {
        return errorCode;
    }
}
