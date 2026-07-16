package com.books.api;

import com.books.model.MetaDTO;
import com.books.service.MetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/meta")
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MetaDTO> getAllBooks() {
        return ok(metaService.getMeta());
    }
}
