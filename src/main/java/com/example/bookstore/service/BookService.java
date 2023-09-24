package com.example.bookstore.service;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstore.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDtoBook);

    List<BookDto> findAll(Pageable pageable);

    List<BookDtoWithoutCategoryIds> findAllByCategories_Id(Long categoryId);

    BookDto getById(Long id);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);

}
