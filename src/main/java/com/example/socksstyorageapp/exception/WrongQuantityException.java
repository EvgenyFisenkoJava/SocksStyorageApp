package com.example.socksstyorageapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Quantity must be at least 1")
public class WrongQuantityException extends Exception{

    public WrongQuantityException(String message) {
        super(message);
    }
}
