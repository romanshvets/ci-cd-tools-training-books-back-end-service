package com.books.service;

import com.books.config.exception.BookValidationException;
import com.books.repository.BookRepository;
import com.books.service.model.BookCreationRequest;
import com.books.utils.BookUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class BookServiceTest {

    @MockitoBean
    private final BookRepository bookRepository;

    private final BookService bookService;

    @Autowired
    public BookServiceTest(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;

        mockRepository();
    }

    public void mockRepository() {
        Mockito.when(bookRepository.getAllBooks()).thenReturn(BookUtils.generateRandomBooks(10));
        Mockito.when(bookRepository.createBook(any(BookCreationRequest.class))).thenReturn(true);
    }

    @Test
    void getAllBooks() {
        var books = bookService.getAllBooks();

        assertNotNull(books);
        assertFalse(books.isEmpty());
    }

    @Test
    void createBookWhenException() {
        assertThrows(BookValidationException.class, () -> bookService.createBook(new BookCreationRequest()));
    }

    @Test
    void createBookWhenSuccess() {
        var request = new BookCreationRequest();
        request.name = "Test Name";
        request.author = "Test Author";
        request.publishDate = "Test Publish Date";

        assertTrue(bookService.createBook(request));
    }

    @Test
    void updateBookWhenException() {

    }

    @Test
    void updateBookWhenSuccess() {
        var request = new BookCreationRequest();
        request.name = "Test Name";
        request.author = "Test Author";
        request.publishDate = "Test Publish Date";

        assertTrue(bookService.createBook(request));
    }

    @Test
    void deleteBook() {

    }
}