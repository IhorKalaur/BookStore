package com.example.bookstore.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookDto {

    private String title;
    private String author;
    private String isbn;
    private String description;
    private String coverImage;
    private Long id;
    private BigDecimal price;

    public BookDto(Long id, String title, String author, String isbn,
                   BigDecimal price, String description, String coverImage) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.coverImage = coverImage;
        this.id = id;
        this.price = price;
    }
}
