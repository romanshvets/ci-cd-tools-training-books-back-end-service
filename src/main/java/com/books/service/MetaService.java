package com.books.service;

import com.books.model.MetaDTO;
import org.springframework.stereotype.Service;

@Service
public class MetaService {

    public MetaDTO getMeta() {
        return new MetaDTO("987654", "2001-02-03 04:05:06");
    }

}
