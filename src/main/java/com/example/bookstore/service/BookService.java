package com.example.bookstore.service;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.CreateBookRequestDto;
import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDtoBook);

    List<BookDto> findAll();

    BookDto getById(Long id);

    BookDto updateById(Long Id, String newTitle, String newAuthor, String newIsbn,
                       BigDecimal newPrice, String newDescription, String newCoverImage);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);

}
