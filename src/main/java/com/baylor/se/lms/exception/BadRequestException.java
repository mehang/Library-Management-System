package com.baylor.se.lms.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class for handling exception for bad request
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
public BadRequestException(){}
public BadRequestException(String message){
    super(message);
}
}
