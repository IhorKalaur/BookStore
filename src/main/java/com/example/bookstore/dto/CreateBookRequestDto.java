package com.example.bookstore.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {

    private String title;
    private String author;
    private String isbn;
    private String description;
    private String coverImage;
    private BigDecimal price;

    public CreateBookRequestDto(String title, String author, String isbn,
                                BigDecimal price, String description, String coverImage) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.description = description;
        this.coverImage = coverImage;
    }
}
