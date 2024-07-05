package com.poppulo.lottery.exception;

import lombok.Data;

@Data
public class AlreadyStatusCheckedException extends RuntimeException {
    private String message;
    private String errorCode;
    public AlreadyStatusCheckedException(String message, String errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
}
