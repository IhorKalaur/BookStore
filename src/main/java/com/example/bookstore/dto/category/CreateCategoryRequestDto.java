package com.example.bookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @NotBlank(message = "required field")
    @Size(min = 5, max = 50, message = "size must be between 5 and 50 character")
    @Pattern(regexp = "^[a-zA-Z']+$", message = "Only Latin letters are allowed")
    private String name;

    @Size(max = 255, message = "size must be less than 255 character")
    private String description;
}
