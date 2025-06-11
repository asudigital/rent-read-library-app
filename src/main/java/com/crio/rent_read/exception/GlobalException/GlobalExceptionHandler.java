package com.crio.rent_read.exception.GlobalException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import com.crio.rent_read.exception.BookNotFoundException;
import com.crio.rent_read.exception.EmailAlreadyExistsException;
import com.crio.rent_read.exception.GeneralException;
import com.crio.rent_read.exception.RentalLimitExceededException;
import com.crio.rent_read.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex){
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex){
        return buildResponse(ex.getMessage() , HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException ex){
     return buildResponse(ex.getMessage() , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RentalLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleRentalLimitExceededException(RentalLimitExceededException ex){
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(GeneralException ex){
       return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUnhandledExceptions(Exception ex){
    return buildResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildResponse(String message, HttpStatus status) {
        ErrorResponse response = new ErrorResponse(message, status, LocalDateTime.now());
        return new ResponseEntity<>(response, status);
    }
   
    @ExceptionHandler(AccessDeniedException.class)
public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", "Access Denied: You don't have permission to perform this action");
    body.put("httpStatus", "FORBIDDEN");
    body.put("localDateTime", LocalDateTime.now().toString());

    return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
}

}
