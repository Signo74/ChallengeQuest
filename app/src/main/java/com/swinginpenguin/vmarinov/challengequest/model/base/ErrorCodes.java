package com.swinginpenguin.vmarinov.challengequest.model.base;

/**
 * Created by vmarinov on 11/7/2014.
 */
public enum ErrorCodes {

    DB_ERROR(1),
    GENERAL_ERROR(0);

    private int errorCode;

    ErrorCodes(int value) {
        this.errorCode = value;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
