package com.baylor.se.lms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundException extends RuntimeException  {
    public NotFoundException(){}
    public NotFoundException(String message){
        super(message);
    }

}
