package com.example.bookstore.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    /*private static final Category FIRST_CATEGORY = new Category(
            1L,
            "First Category",
            "This is first category");

    private static final Category SECOND_CATEGORY = Category.builder()
            .id(2L)
            .name("Second Category")
            .description("This is second category")
            .build();

    private static final CreateBookRequestDto CREATE_FIRST_BOOK_REQUEST_DTO = CreateBookRequestDto
            .builder()
            .title("First Book")
            .author("First Author")
            .isbn("1234567890")
            .price(BigDecimal.valueOf(100))
            .description("This is first book")
            .coverImage("http://example.firstPicture")
            .categoryIds(Set.of(1L, 2L))
            .build();

    private static final CreateBookRequestDto CREATE_FIRST_BOOK_REQUEST_DTO_INVALID
            = CreateBookRequestDto.builder()
            .title("First Book")
            .author("First Author")
            .isbn("1234567890")
            .price(BigDecimal.valueOf(100))
            .description("This is first book")
            .coverImage("http://example.firstPicture")
            .categoryIds(Set.of(3L))
            .build();
    private static final BookDto FIRST_BOOK_DTO = BookDto.builder()
            .id(1L)
            .title("First Book")
            .author("First Author")
            .isbn("1234567890")
            .price(BigDecimal.valueOf(100))
            .description("This is first book")
            .coverImage("http://example.firstPicture")
            .categoryIds(Set.of(1L, 2L))
            .build();

    private static final Book FIRST_BOOK = Book.builder()
            .id(1L)
            .title("First Book")
            .author("First Author")
            .isbn("1234567890")
            .price(BigDecimal.valueOf(100))
            .description("This is first book")
            .coverImage("http://example.firstPicture")
            .categories(Set.of(FIRST_CATEGORY, SECOND_CATEGORY))
            .build();

    private static final BookDto SECOND_BOOK_DTO = BookDto.builder()
            .id(2L)
            .title("Second Book")
            .author("Second Author")
            .isbn("1234567890")
            .price(BigDecimal.valueOf(100))
            .description("This is Second book")
            .coverImage("http://example.secondPicture")
            .categoryIds(Set.of(2L))
            .build();

    private static final BookDtoWithoutCategoryIds BOOK_DTO_WITHOUT_CATEGORY_IDS =
            BookDtoWithoutCategoryIds.builder()
            .id(2L)
            .title("Second Book")
            .author("Second Author")
            .isbn("1234567890")
            .price(BigDecimal.valueOf(100))
            .description("This is Second book")
            .coverImage("http://example.secondPicture")
            .build();
    private static final Book SECOND_BOOK = Book.builder()
            .id(2L)
            .title("Second Book")
            .author("Second Author")
            .isbn("1234567890")
            .price(BigDecimal.valueOf(100))
            .description("This is second book")
            .coverImage("http://example.secondPicture")
            .categories(Set.of(SECOND_CATEGORY))
            .build();

    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @Test
    @DisplayName("""
            Save book with valid category ids
            """)
    void save_withValidCategoryIds_shouldReturnBookDto() {
        when(bookMapper.toEntity(CREATE_FIRST_BOOK_REQUEST_DTO)).thenReturn(FIRST_BOOK);
        when(bookRepository.save(FIRST_BOOK)).thenReturn(FIRST_BOOK);
        when(bookMapper.toDto(FIRST_BOOK)).thenReturn(FIRST_BOOK_DTO);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(FIRST_CATEGORY));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(SECOND_CATEGORY));

        BookDto actual = bookServiceImpl.save(CREATE_FIRST_BOOK_REQUEST_DTO);

        assertThat(FIRST_BOOK_DTO).isEqualTo(actual);
    }

    @Test
    @DisplayName("""
            Save book with InValid category ids
            """)
    void save_withInvalidCategoryIds_shouldThrowException() {
        EntityNotFoundException actual = assertThrows(EntityNotFoundException.class,
                () -> bookServiceImpl.save(CREATE_FIRST_BOOK_REQUEST_DTO_INVALID));

        assertEquals("Category with id: 3 not found", actual.getMessage());
    }

    @Test
    void findAll_withPage10_shouldReturnTwoElements() {
        Pageable pageable = Pageable.unpaged();
        List<Book> books = List.of(FIRST_BOOK, SECOND_BOOK);
        //Page<Book> bookPage = new PageImpl<>(books, pageable, 2);

        //when(bookRepository.findAllWithCategories(pageable)).thenReturn(bookPage);
        when(bookRepository.findAllWithCategories(pageable)).thenReturn(books);

        when(bookMapper.toDto(FIRST_BOOK)).thenReturn(FIRST_BOOK_DTO);
        when(bookMapper.toDto(SECOND_BOOK)).thenReturn(SECOND_BOOK_DTO);

        List<BookDto> bookDtos = bookServiceImpl.findAll(pageable);

        assertThat(bookDtos).hasSize(2);
        assertThat(bookDtos.get(0)).isEqualTo(FIRST_BOOK_DTO);
        assertThat(bookDtos.get(1)).isEqualTo(SECOND_BOOK_DTO);

        verify(bookMapper, times(2)).toDto(any(Book.class));
    }

    @Test
    void findAllByCategories_Id() {
        List<Book> books = List.of(SECOND_BOOK);

        when(bookRepository.findAllByCategories_Id(anyLong())).thenReturn(books);
        when(bookMapper.toBookDtoWithoutCategoryIds(any(Book.class)))
                .thenReturn(BOOK_DTO_WITHOUT_CATEGORY_IDS);

        List<BookDtoWithoutCategoryIds> bookDtoWithoutCategoryIds
                = bookServiceImpl.findAllByCategories_Id(anyLong());

        assertThat(bookDtoWithoutCategoryIds).hasSize(1);
        assertThat(bookDtoWithoutCategoryIds.get(0)).isEqualTo(BOOK_DTO_WITHOUT_CATEGORY_IDS);

    }

    @Test
    @DisplayName("Must be true")
    void getById_withValidUserId_shouldReturnBookDto() {
        Long bookId = 1L;
        Book book = Book.builder()
                .id(1L).title("Book 1")
                .author("Some author").price(BigDecimal.TEN).isbn("12345678")
                .description("Some description").coverImage("http:/example").build();

        BookDto bookDto = BookDto.builder()
                .id(1L).title("Book 1")
                .author("Some author").price(BigDecimal.TEN).isbn("12345678")
                .description("Some description").coverImage("http://example").build();

        when(bookRepository.findByIdWithCategories(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookServiceImpl.getById(book.getId());

        BookDto expected = bookDto;
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Must throws Exc")
    void getById_withNonExistUserId_ShouldThrowException() {
        //Given
        Long bookId = 1L;

        when(bookRepository.findByIdWithCategories(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> bookServiceImpl.getById(bookId)
        );

        //Then
        String expected = "Can't get book by id: " + bookId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }*/
}
