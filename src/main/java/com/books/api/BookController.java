package com.books.api;

import com.books.model.BookDTO;
import com.books.service.BookService;
import com.books.service.model.BookCreationRequest;
import com.books.service.model.BookUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ok(bookService.getAllBooks());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> createBook(@RequestBody BookCreationRequest request) {
        return ok(bookService.createBook(request));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookUpdateRequest request) {
        return ok(bookService.updateBook(request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable Long id) {
        return ok(bookService.deleteBook(id));
    }
}
