package com.books.service;

import com.books.config.exception.BookMissingException;
import com.books.config.exception.BookValidationException;
import com.books.model.BookDTO;
import com.books.repository.BookRepository;
import com.books.service.model.BookCreationRequest;
import com.books.service.model.BookUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.books.utils.BookUtils.validateBookCreation;
import static com.books.utils.BookUtils.validateBookUpdate;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Set<BookDTO> getAllBooks() {
        return repository.getAllBooks();
    }

    public boolean createBook(BookCreationRequest request) {
        validateBookCreation(request).ifPresent(errors -> {
            throw new BookValidationException(errors);
        });

        return repository.createBook(request);
    }

    public boolean updateBook(BookUpdateRequest request) {
        validateBookUpdate(request).ifPresent(errors -> {
            throw new BookValidationException(errors);
        });

        if (repository.getBookById(request.id).isEmpty()) {
            throw new BookMissingException(request.id);
        }

        return repository.updateBook(request);
    }

    public boolean deleteBook(Long id) {
        return repository.deleteBook(id);
    }
}
