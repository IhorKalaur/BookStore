package com.example.bookstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstore.dto.book.CreateBookRequestDto;
import com.example.bookstore.exceptions.EntityNotFoundException;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    private static final Long ID_ONE = 1L;
    private static final String BOOK_NOT_FOUND_MESSAGE = "Can't find book by id: ";
    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Can't find category by id: ";

    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void save_withValidCategoryIds_shouldReturnBookDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setCategoryIds(new HashSet<>(Collections.singletonList(ID_ONE)));
        Book bookEntity = new Book();
        bookEntity.setId(ID_ONE);
        BookDto expected = new BookDto();

        when(bookMapper.toEntity(requestDto)).thenReturn(bookEntity);
        when(categoryRepository.findById(ID_ONE)).thenReturn(Optional.of(new Category()));
        when(bookRepository.save(any(Book.class))).thenReturn(bookEntity);
        when(bookMapper.toDto(bookEntity)).thenReturn(expected);

        BookDto actual = bookService.save(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    public void save_withInvalidCategoryIds_shouldThrowEntityNotFoundException() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setCategoryIds(new HashSet<>(Collections.singletonList(ID_ONE)));

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.save(requestDto));
        assertEquals(CATEGORY_NOT_FOUND_MESSAGE + ID_ONE, exception.getMessage());
    }

    @Test
    public void save_withNullCategoryIds_shouldReturnBookDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        Book bookEntity = new Book();
        bookEntity.setId(1L);
        BookDto expected = new BookDto();

        when(bookMapper.toEntity(requestDto)).thenReturn(bookEntity);
        when(bookRepository.save(any(Book.class))).thenReturn(bookEntity);
        when(bookMapper.toDto(bookEntity)).thenReturn(expected);

        BookDto actual = bookService.save(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    public void findAll_withBooksInRepository_shouldReturnBookDtoList() {
        Pageable pageable = Pageable.unpaged();
        Book firstBook = new Book();
        Book secondBook = new Book();
        BookDto firstBookDto = new BookDto();
        BookDto secondBookDto = new BookDto();

        when(bookRepository.findAllWithCategories(pageable))
                .thenReturn(List.of(firstBook, secondBook));
        when(bookMapper.toDto(firstBook)).thenReturn(firstBookDto);
        when(bookMapper.toDto(secondBook)).thenReturn(secondBookDto);

        List<BookDto> actual = bookService.findAll(pageable);

        assertEquals(2, actual.size());
        assertEquals(firstBookDto, actual.get(0));
        assertEquals(secondBookDto, actual.get(1));
    }

    @Test
    public void findAll_withEmptyRepository_shouldReturnEmptyList() {
        Pageable pageable = Pageable.unpaged();

        when(bookRepository.findAllWithCategories(pageable)).thenReturn(List.of());

        List<BookDto> actual = bookService.findAll(pageable);

        assertEquals(0, actual.size());
    }

    @Test
    public void findAllByCategoryId_withBooksInRepository_shouldReturnBookDtoWithoutCategoryIds() {
        Long categoryId = 1L;
        Book firstBook = new Book();
        Book secondBook = new Book();
        BookDtoWithoutCategoryIds firstBookDto = new BookDtoWithoutCategoryIds();
        BookDtoWithoutCategoryIds secondBookDto = new BookDtoWithoutCategoryIds();

        when(bookRepository.findAllByCategories_Id(categoryId))
                .thenReturn(List.of(firstBook, secondBook));
        when(bookMapper.toBookDtoWithoutCategoryIds(firstBook)).thenReturn(firstBookDto);
        when(bookMapper.toBookDtoWithoutCategoryIds(secondBook)).thenReturn(secondBookDto);

        List<BookDtoWithoutCategoryIds> actual = bookService.findAllByCategories_Id(categoryId);

        assertEquals(2, actual.size());
        assertEquals(firstBookDto, actual.get(0));
        assertEquals(secondBookDto, actual.get(1));
    }

    @Test
    public void findAllByCategoryId_withEmptyRepository_shouldReturnEmptyList() {
        Long categoryId = ID_ONE;

        when(bookRepository.findAllByCategories_Id(categoryId)).thenReturn(List.of());

        List<BookDtoWithoutCategoryIds> actual = bookService.findAllByCategories_Id(categoryId);

        assertEquals(0, actual.size());
    }

    @Test
    public void getById_withExistingBook_shouldReturnBookDto() {
        Long bookId = ID_ONE;
        Book book = new Book();
        BookDto bookDto = new BookDto();

        when(bookRepository.findByIdWithCategories(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.getById(bookId);

        assertEquals(bookDto, actual);
    }

    @Test
    public void getById_withNonExistingBook_shouldThrowEntityNotFoundException() {
        Long bookId = ID_ONE;

        when(bookRepository.findByIdWithCategories(bookId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.getById(bookId));

        assertEquals(BOOK_NOT_FOUND_MESSAGE + bookId, exception.getMessage());
    }

    @Test
    public void update_withExistingBook_shouldReturnUpdatedBookDto() {
        Long bookId = 1L;
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        Book existingBook = new Book();
        existingBook.setId(bookId);
        BookDto updatedBookDto = new BookDto();

        when(bookRepository.existsById(bookId)).thenReturn(true);
        when(bookMapper.toEntity(requestDto)).thenReturn(existingBook);
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);
        when(bookMapper.toDto(existingBook)).thenReturn(updatedBookDto);

        BookDto actual = bookService.update(bookId, requestDto);

        assertEquals(updatedBookDto, actual);
    }

    @Test
    public void update_withNonExistingBook_shouldThrowEntityNotFoundException() {
        Long bookId = ID_ONE;
        CreateBookRequestDto requestDto = new CreateBookRequestDto();

        when(bookRepository.existsById(bookId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.update(bookId, requestDto));
        assertEquals(BOOK_NOT_FOUND_MESSAGE + bookId, exception.getMessage());
    }

    @Test
    public void deleteById_withExistingBook_shouldDeleteBook() {
        Long bookId = 1L;

        bookService.deleteById(bookId);

        verify(bookRepository, Mockito.times(1)).deleteById(bookId);
    }
}
