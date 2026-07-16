package com.books.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(BookValidationException.class)
    public ResponseEntity<String> handleValidationException(BookValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Bad Request");
    }

    @ExceptionHandler(BookMissingException.class)
    public ResponseEntity<String> handleSimpleException(BookMissingException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Not Found");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred on the server");
    }
}


