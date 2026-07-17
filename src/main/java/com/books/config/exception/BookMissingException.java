package com.books.config.exception;

public class BookMissingException extends RuntimeException {

    private final Long bookId;

    public BookMissingException(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }
}
