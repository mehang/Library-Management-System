package com.baylor.se.lms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handles Not Available Exception
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAvailableException extends RuntimeException  {
    public NotAvailableException(){}
    public NotAvailableException(String message){
        super(message);
    }

}
