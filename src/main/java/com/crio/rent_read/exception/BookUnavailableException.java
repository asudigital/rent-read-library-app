package com.crio.rent_read.exception;

public class BookUnavailableException extends RuntimeException {
    public BookUnavailableException(String message){
        super(message);
    }
}
