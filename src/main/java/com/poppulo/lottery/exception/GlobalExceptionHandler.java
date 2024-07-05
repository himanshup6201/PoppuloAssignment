package com.poppulo.lottery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LotteryNotFoundException.class)
    public ResponseEntity<ErrorResponse> lotteryNotFoundExceptionHandler(LotteryNotFoundException ex){
        String message = ex.getMessage();
        ErrorResponse apiResponse = new ErrorResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultException.class)
    public ResponseEntity<ErrorResponse> emptyResultExceptionHandler(EmptyResultException ex){
        String message = ex.getMessage();
        ErrorResponse apiResponse = new ErrorResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyStatusCheckedException.class)
    public ResponseEntity<ErrorResponse> emptyResultExceptionHandler(AlreadyStatusCheckedException ex){
        String message = ex.getMessage();
        ErrorResponse apiResponse = new ErrorResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
