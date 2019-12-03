package com.baylor.se.lms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
public class NotAvailableException extends RuntimeException {
    public NotAvailableException(){}
    public NotAvailableException(String message){
        super(message);
    }
}
