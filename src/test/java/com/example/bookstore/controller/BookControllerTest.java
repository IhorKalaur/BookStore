package com.example.bookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookControllerTest {
    private static final Long ID_ONE = 1L;
    private static final Long ID_TWO = 2L;
    private static final String FIRST_BOOK_TITLE = "First Book";
    private static final String FIRST_BOOK_TITLE_UPDATED = "First Book Updated";
    private static final String SECOND_BOOK_TITLE = "Second Book";
    private static final String AUTHOR = "Author";
    private static final String FIRST_BOOK_ISBN = "1234567891";
    private static final String FIRST_BOOK_ISBN_UPDATED = "1234567811";
    private static final String SECOND_BOOK_ISBN = "1234567892";
    private static final BigDecimal FIRST_BOOK_PRICE = BigDecimal.valueOf(199.99);
    private static final BigDecimal FIRST_BOOK_PRICE_UPDATED = BigDecimal.valueOf(399.99);
    private static final BigDecimal SECOND_BOOK_PRICE = BigDecimal.valueOf(299.99);

    private static MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @WithMockUser(username = "user")
    @Test
    @Sql(
            scripts = {
                    "classpath:database/books/add-first-book-into-books-table.sql",
                    "classpath:database/books/add-second-book-into-books-table.sql"
            }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = "classpath:database/books/delete-all-from-books-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("Test getAll() without pagination")
    void getAll_ShouldReturnListOfBooks() throws Exception {
        BookDto firstBookDto = new BookDto();
        firstBookDto.setId(ID_ONE);
        firstBookDto.setTitle(FIRST_BOOK_TITLE);
        firstBookDto.setAuthor(AUTHOR);
        firstBookDto.setIsbn(FIRST_BOOK_ISBN);
        firstBookDto.setPrice(FIRST_BOOK_PRICE);

        BookDto secondBookDto = new BookDto();
        secondBookDto.setId(ID_TWO);
        secondBookDto.setTitle(SECOND_BOOK_TITLE);
        secondBookDto.setAuthor(AUTHOR);
        secondBookDto.setIsbn(SECOND_BOOK_ISBN);
        secondBookDto.setPrice(SECOND_BOOK_PRICE);

        List<BookDto> expected = List.of(firstBookDto, secondBookDto);
        MvcResult mvcResult = mockMvc.perform(get("/books")).andReturn();

        List<BookDto> actual = Arrays.asList(objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), BookDto[].class));

        assertEquals(2, actual.size());
        assertTrue(EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0),
                "description", "coverImage", "categoryIds"));
        assertTrue(EqualsBuilder.reflectionEquals(expected.get(1), actual.get(1),
                "description", "coverImage", "categoryIds"));
    }

    @WithMockUser(username = "user")
    @Test
    @Sql(
            scripts = {
                    "classpath:database/books/add-first-book-into-books-table.sql",
                    "classpath:database/books/add-second-book-into-books-table.sql"
            }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = "classpath:database/books/delete-all-from-books-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("Test getById() with a correct id")
    void getById_ShouldReturnBookDto() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/books/" + ID_ONE)).andReturn();

        BookDto actual =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        BookDto.class);

        assertEquals(ID_ONE, actual.getId());
        assertEquals(FIRST_BOOK_TITLE, actual.getTitle());
        assertEquals(AUTHOR, actual.getAuthor());
        assertEquals(FIRST_BOOK_ISBN, actual.getIsbn());
        assertEquals(FIRST_BOOK_PRICE, actual.getPrice());
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @Sql(
            scripts = "classpath:database/books/delete-all-from-books-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("test create() with valid data")
    void createBook_ShouldCreateNewBook() throws Exception {
        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto();
        bookRequestDto.setTitle(FIRST_BOOK_TITLE);
        bookRequestDto.setAuthor(AUTHOR);
        bookRequestDto.setIsbn(FIRST_BOOK_ISBN);
        bookRequestDto.setPrice(FIRST_BOOK_PRICE);

        MvcResult mvcResult = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto createdBookDto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), BookDto.class);

        assertNotNull(createdBookDto.getId());
        assertEquals(bookRequestDto.getTitle(), createdBookDto.getTitle());
        assertEquals(bookRequestDto.getAuthor(), createdBookDto.getAuthor());
        assertEquals(bookRequestDto.getIsbn(), createdBookDto.getIsbn());
        assertEquals(bookRequestDto.getPrice(), createdBookDto.getPrice());
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @Sql(
            scripts = {
                    "classpath:database/books/add-first-book-into-books-table.sql"
            }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = "classpath:database/books/delete-all-from-books-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("test update() with valid data")
    void update() throws Exception {
        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto();
        bookRequestDto.setTitle(FIRST_BOOK_TITLE_UPDATED);
        bookRequestDto.setAuthor(AUTHOR);
        bookRequestDto.setIsbn(FIRST_BOOK_ISBN_UPDATED);
        bookRequestDto.setPrice(FIRST_BOOK_PRICE_UPDATED);

        MvcResult mvcResult = mockMvc.perform(put("/books/" + ID_ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequestDto)))
                .andExpect(status().isOk())
                .andReturn();

        BookDto updated = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), BookDto.class);

        assertNotNull(updated.getId());
        assertEquals(ID_ONE, updated.getId());
        assertEquals(bookRequestDto.getTitle(), updated.getTitle());
        assertEquals(bookRequestDto.getAuthor(), updated.getAuthor());
        assertEquals(bookRequestDto.getIsbn(), updated.getIsbn());
        assertEquals(bookRequestDto.getPrice(), updated.getPrice());

    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @Sql(
            scripts = {
                    "classpath:database/books/add-first-book-into-books-table.sql"
            }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = "classpath:database/books/delete-all-from-books-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("test deleteById() with valid data")
    void deleteById() throws Exception {
        mockMvc.perform(delete("/books/" + ID_ONE))
                .andExpect(status().isNoContent());
    }
}
