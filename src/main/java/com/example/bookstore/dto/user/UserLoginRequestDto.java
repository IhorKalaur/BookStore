package com.example.bookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotBlank
        @Email(message = "must match the email format")
        String email,
        @NotBlank
        @Size(min = 8, max = 255, message = "")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]*$",
                message = "Password must contain at least one letter, one digit and one symbol")
        String password
) {
}
