package com.books.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.lang.String.format;

@RestControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(BookValidationException.class)
    public ResponseEntity<String> handleValidationException(BookValidationException e) {
        var message = e.getFieldNames() != null && !e.getFieldNames().isEmpty() ?
                format("Bad Request. Fields are invalid: %s", String.join(", ", e.getFieldNames())) : "Bad Request";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    @ExceptionHandler(BookMissingException.class)
    public ResponseEntity<String> handleSimpleException(BookMissingException e) {
        var message = e.getBookId() != null && e.getBookId() > 0L ?
                format("Book %s does not exist", e.getBookId()) : "Not Found";

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred on the server");
    }
}


