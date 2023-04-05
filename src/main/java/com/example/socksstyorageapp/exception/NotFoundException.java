package com.example.socksstyorageapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Socks are not found in database")
public class NotFoundException extends Exception{

    public NotFoundException(String message) {
        super(message);
    }
}
