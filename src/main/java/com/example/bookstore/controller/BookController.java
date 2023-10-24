package com.example.bookstore.controller;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.book.CreateBookRequestDto;
import com.example.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for maneging books")
@RestController
@RequestMapping(value = "/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all books",
            description = "Get list of available books")
    public List<BookDto> getAll(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "5") int size) {
        Pageable updatedPageable = PageRequest.of(page - 1, size, Sort.unsorted());
        return bookService.findAll(updatedPageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get book by id",
            description = "Get book by id, if it available")
    public BookDto getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new book",
            description = "Save new book to DB with unique ISBN number. Only for admin")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a book",
            description = "Update a book's information "
                    + "in the database using the provided book data. Only for admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto update(@PathVariable Long id,
                          @RequestBody @ Valid CreateBookRequestDto bookRequestDto) {
        return bookService.update(id, bookRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete a book by id",
            description = "Mark a book as deleted. Only for admin")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
