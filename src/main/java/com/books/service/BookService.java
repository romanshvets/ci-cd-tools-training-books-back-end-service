package com.books.service;

import com.books.model.BookDTO;
import com.books.repository.BookRepository;
import com.books.service.model.BookCreationRequest;
import com.books.service.model.BookUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<BookDTO> getAllBooks() {
        return repository.getAllBooks();
    }

    public BookDTO createBook(BookCreationRequest request) {
        return repository.createBook(request);
    }

    public BookDTO updateBook(BookUpdateRequest request) {
        return repository.updateBook(request);
    }

    public boolean deleteBook(Long id) {
        return repository.deleteBook(id);
    }
}
