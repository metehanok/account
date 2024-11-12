package com.folksdev.account.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String message){
        super(message);
    }
}
