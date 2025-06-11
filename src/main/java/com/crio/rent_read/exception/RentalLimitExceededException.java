package com.crio.rent_read.exception;

public class RentalLimitExceededException extends RuntimeException{
    
    public RentalLimitExceededException(String message){
        super(message);
    }
}
