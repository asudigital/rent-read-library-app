package com.crio.rent_read.exception;

public class RentalNotFoundException extends RuntimeException {
    
    public RentalNotFoundException(String message){
      super(message);
    }
}
