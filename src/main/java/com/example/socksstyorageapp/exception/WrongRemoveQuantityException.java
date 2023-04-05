package com.example.socksstyorageapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Quantity to remove is more than quantity in database")
public class WrongRemoveQuantityException extends Exception{

    public WrongRemoveQuantityException(String message) {
        super(message);
    }
}
