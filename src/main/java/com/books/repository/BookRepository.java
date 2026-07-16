package com.books.repository;

import com.books.model.BookDTO;
import com.books.service.model.BookCreationRequest;
import com.books.service.model.BookUpdateRequest;
import com.books.utils.BookUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookRepository {

    private final Map<Long, BookDTO> BOOKS;
    private final AtomicLong BOOK_ID_GENERATOR;

    public BookRepository() {
        BOOKS = BookUtils.generateRandomBooks(10);

        BOOK_ID_GENERATOR = new AtomicLong(BOOKS.keySet().stream().mapToLong(i -> i).max().orElse(0L));
    }

    public List<BookDTO> getAllBooks() {
        return BOOKS.values().stream().toList();
    }

    public BookDTO createBook(BookCreationRequest request) {
        return BOOKS.computeIfAbsent(BOOK_ID_GENERATOR.incrementAndGet(),
                (id) -> new BookDTO(id, request.name, request.author, request.publishDate)
        );
    }

    public BookDTO updateBook(BookUpdateRequest request) {
        return BOOKS.computeIfPresent(request.id, (id, book) -> new BookDTO(id, request.name, request.author, request.publishDate));
    }

    public boolean deleteBook(Long id) {
        return BOOKS.remove(id) != null;
    }
}
