package com.baylor.se.lms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class for unmatched password exception
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnmatchingPasswordException extends RuntimeException  {
    public UnmatchingPasswordException(){}
    public UnmatchingPasswordException(String message){
        super(message);
    }

}
