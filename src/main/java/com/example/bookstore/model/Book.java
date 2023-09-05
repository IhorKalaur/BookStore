package com.example.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
@Table(name = "books")

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false,
            name = "title")
    private String title;
    @Column(name = "author",
            nullable = false)
    private String author;
    @Column(name = "isbn",
            nullable = false,
            unique = true)
    private String isbn;
    @Column(name = "price",
            nullable = false,
            columnDefinition = "BIGINT CHECK (price >= 0)"
    )
    private BigDecimal price;
    @Column(name = "description")
    private String description;
    @Column(name = "coverImage")
    private String coverImage;
}
