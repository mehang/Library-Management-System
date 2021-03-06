package com.baylor.se.lms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handles Invalid Email Exception
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEmailException extends RuntimeException  {
    public InvalidEmailException(){}
    public InvalidEmailException(String msg){
        super(msg);
    }

}
