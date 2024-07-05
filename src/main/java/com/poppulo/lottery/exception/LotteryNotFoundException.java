package com.poppulo.lottery.exception;

public class LotteryNotFoundException extends RuntimeException {
    String message;
    String errorCode;

    public LotteryNotFoundException(String message, String errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
}
