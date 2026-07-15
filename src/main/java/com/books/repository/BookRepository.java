package com.books.repository;

import com.books.model.BookDTO;
import com.books.service.model.BookCreationRequest;
import com.books.service.model.BookUpdateRequest;
import com.books.utils.BookUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepository {

    private static final ConcurrentHashMap<Long, BookDTO> BOOKS = BookUtils.generateRandomBooks(25);

    public List<BookDTO> getAllBooks() {
        return BOOKS.values().stream().toList();
    }

    public BookDTO createBook(BookCreationRequest request) {
        var maxId = BOOKS.keySet().stream().mapToLong(i -> i).max().orElse(0);
        var newId = maxId + 1;

        BOOKS.computeIfAbsent(newId, (id) -> new BookDTO(id, request.name, request.author, request.publishDate));

        return BOOKS.get(newId);
    }

    public BookDTO updateBook(BookUpdateRequest request) {
        return null;
    }

    public boolean deleteBook(Long id) {
        return false;
    }

}
