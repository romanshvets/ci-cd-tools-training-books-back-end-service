package com.books;

import com.books.repository.BookRepository;
import com.books.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class ApplicationTests {

    @MockitoBean
    private final BookRepository bookRepository;

    private final BookService bookService;

    @Autowired
    public ApplicationTests(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(bookService);
        Assertions.assertNotNull(bookRepository);
    }
}
