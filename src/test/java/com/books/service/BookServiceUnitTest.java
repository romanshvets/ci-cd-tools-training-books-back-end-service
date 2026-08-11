package com.books.service;

import com.books.config.exception.BookValidationException;
import com.books.repository.BookRepository;
import com.books.service.model.BookCreationRequest;
import com.books.service.model.BookUpdateRequest;
import com.books.utils.BookUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceUnitTest {

    @MockitoBean
    private final BookRepository bookRepository;

    private final BookService bookService;

    @Autowired
    public BookServiceUnitTest(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;

        mockRepository();
    }

    public void mockRepository() {
        var mockBooks = BookUtils.generateRandomBooks(10);

        var randomBook = mockBooks.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find any book"));

        when(bookRepository.getAllBooks()).thenReturn(mockBooks);
        when(bookRepository.getBookById(eq(randomBook.getId()))).thenReturn(Optional.of(randomBook));
        when(bookRepository.createBook(any(BookCreationRequest.class))).thenReturn(true);
        when(bookRepository.updateBook(any(BookUpdateRequest.class))).thenReturn(true);
        when(bookRepository.deleteBook(eq(randomBook.getId()))).thenReturn(true);
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
        assertThrows(BookValidationException.class, () -> bookService.updateBook(new BookUpdateRequest()));
    }

    @Test
    void updateBookWhenSuccess() {
        var randomBook = bookService.getAllBooks()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find any book"));

        var request = new BookUpdateRequest();
        request.id = randomBook.getId();
        request.name = "Test Name";
        request.author = "Test Author";
        request.publishDate = "Test Publish Date";

        assertTrue(bookService.updateBook(request));
    }

    @Test
    void deleteExistingBook() {
        var randomBook = bookService.getAllBooks()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find any book"));

        assertTrue(bookService.deleteBook(randomBook.getId()));
    }

    @Test
    void deleteNonExistingBook() {
        var randomBook = bookService.getAllBooks()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find any book"));

        assertFalse(bookService.deleteBook(-1L));
    }
}