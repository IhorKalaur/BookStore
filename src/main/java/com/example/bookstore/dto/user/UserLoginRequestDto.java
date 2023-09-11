package com.example.bookstore.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotEmpty
        @Size(min = 5, max = 20)
        String email,
        @NotEmpty
        @Size(min = 5, max = 20)
        String password
) {
}
