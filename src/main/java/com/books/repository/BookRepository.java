package com.books.repository;

import com.books.model.BookDTO;
import com.books.service.model.BookCreationRequest;
import com.books.service.model.BookUpdateRequest;
import com.books.utils.BookUtils;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookRepository {

    private final Set<BookDTO> BOOKS;
    private final AtomicLong BOOK_ID_GENERATOR;

    public BookRepository() {
        BOOKS = BookUtils.generateRandomBooks(10);

        BOOK_ID_GENERATOR = new AtomicLong(BOOKS.stream().mapToLong(BookDTO::getId).max().orElse(0L));
    }

    public Set<BookDTO> getAllBooks() {
        return BOOKS;
    }

    public Optional<BookDTO> getBookById(Long id) {
        return BOOKS.stream().filter(b -> Objects.equals(id, b.getId())).findFirst();
    }

    public boolean createBook(BookCreationRequest request) {
        return BOOKS.add(new BookDTO(BOOK_ID_GENERATOR.incrementAndGet(), request.name, request.author, request.publishDate));
    }

    public boolean updateBook(BookUpdateRequest request) {
        var book = BOOKS.stream()
                .filter(b -> Objects.equals(request.id, b.getId()))
                .findFirst().orElse(null);

        if (book == null) {
            return false;
        }

        book.setAuthor(request.author);
        book.setName(request.name);
        book.setPublishDate(request.publishDate);

        return true;
    }

    public boolean deleteBook(Long id) {
        var book = BOOKS.stream()
                .filter(b -> Objects.equals(id, b.getId()))
                .findFirst().orElse(null);

        if (book == null) {
            return false;
        }

        return BOOKS.remove(book);
    }
}
