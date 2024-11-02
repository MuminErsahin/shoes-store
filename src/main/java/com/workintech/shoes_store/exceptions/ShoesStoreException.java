package com.workintech.shoes_store.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ShoesStoreException extends RuntimeException {
    private HttpStatus httpStatus;

    public ShoesStoreException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
