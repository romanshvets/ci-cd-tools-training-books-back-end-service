package com.books.api;

import com.books.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> createResource(HttpServletRequest request) {
        var resourceId = this.bookService.createResource(request);

        return ResponseEntity.ok(Collections.singletonMap("id", resourceId));
    }

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    public ResponseEntity<byte[]> getResource(@PathVariable("id") String id) {
        var resourceContent = this.bookService.getResource(id);

        return ResponseEntity.ok(resourceContent);
    }

    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Set<Long>>> deleteSongs(@RequestParam("id") String ids) {
        var deletedResourceIds = this.bookService.deleteResources(ids);

        return ResponseEntity.ok(Collections.singletonMap("ids", deletedResourceIds));
    }
}
