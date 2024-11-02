package com.workintech.shoes_store.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(ShoesStoreException shoesStoreException){
        ErrorResponse errorResponse = new ErrorResponse(shoesStoreException.getMessage());
        log.error(errorResponse.getMessage());
        return new ResponseEntity<>(errorResponse,shoesStoreException.getHttpStatus());
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(Exception exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        log.warn(errorResponse.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
