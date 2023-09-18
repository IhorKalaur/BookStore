package com.example.bookstore.dto.book;

import com.example.bookstore.validator.Isbn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateBookRequestDto {
    @NotBlank(message = "Can't be null or empty")
    @Length(min = 3, max = 50, message = "Length must be more than  3, but less than  50")
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,'-]+$",
            message = "Only Latin letters, digits, and special characters are allowed")
    private String title;
    @NotBlank(message = "Can't be null or empty")
    @Length(min = 5, max = 50, message = "Length must be more than  5, but less than  50")
    @Pattern(regexp = "^[A-Za-z\\s']+$", message = "Only Latin letters are allowed")
    private String author;
    @NotNull
    @Isbn
    private String isbn;
    @NotNull
    @PositiveOrZero(message = "Only non-negative price are allowed")
    private BigDecimal price;
    private String description;
    private String coverImage;
}
