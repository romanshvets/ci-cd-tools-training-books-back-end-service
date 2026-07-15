package com.books.model;

public class BookDTO {

    private final Long id;
    private final String name;
    private final String author;
    private final String publishDate;

    public BookDTO(Long id, String name, String author, String publishDate) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publishDate = publishDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishDate() {
        return publishDate;
    }
}
