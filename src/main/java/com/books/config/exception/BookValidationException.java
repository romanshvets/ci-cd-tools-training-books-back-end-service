package com.books.config.exception;

import java.util.Set;

public class BookValidationException extends RuntimeException {

    private final Set<String> fieldNames;

    public BookValidationException(Set<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public Set<String> getFieldNames() {
        return fieldNames;
    }
}
