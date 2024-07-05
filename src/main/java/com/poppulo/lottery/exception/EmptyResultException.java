package com.poppulo.lottery.exception;

import lombok.Data;

@Data
public class EmptyResultException extends RuntimeException {
    private String message;
    private String errorCode;

    public EmptyResultException(String message, String errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
}
