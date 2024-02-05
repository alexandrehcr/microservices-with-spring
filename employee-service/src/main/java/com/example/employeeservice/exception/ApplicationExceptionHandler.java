package com.example.employeeservice.exception;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApplicationErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ApplicationErrorResponse(ex.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ApplicationErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(new ApplicationErrorResponse(ex.getMessage()), CONFLICT);
    }

    @ExceptionHandler({ConversionFailedException.class})
    public ResponseEntity<ApplicationErrorResponse> handleConversion(ConversionFailedException ex) {
        return new ResponseEntity<>(new ApplicationErrorResponse(ex.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApplicationErrorResponse> genericExceptionHandler(Exception ex) {
        return new ResponseEntity<>(new ApplicationErrorResponse(ex.getMessage()), INTERNAL_SERVER_ERROR);
    }
}
