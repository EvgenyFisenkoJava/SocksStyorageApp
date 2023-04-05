package com.example.socksstyorageapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "CottonPart must be between 0 and 100")
public class WrongCottonPartException extends Exception{
    public WrongCottonPartException(String message) {
        super(message);
    }
}
