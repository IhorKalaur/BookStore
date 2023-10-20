package com.example.bookstore.dto.user;

import com.example.bookstore.validator.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@FieldMatch(first = "password", second = "repeatPassword",
        message = "passwords must match")
public class UserRegistrationRequestDto {
    @NotBlank(message = "required field")
    @Size(min = 5, max = 50, message = "size must be between 8 and 50 character")
    @Email(message = "must match the email format")
    private String email;

    @NotBlank(message = "required field")
    @Size(min = 8, max = 50, message = "size must be between 8 and 50 character")
    private String password;

    @NotBlank(message = "required field")
    @Size(min = 8, max = 50, message = "size must be between 8 and 50 character")
    private String repeatPassword;

    @NotBlank(message = "required field")
    private String firstName;

    @NotBlank(message = "required field")
    private String lastName;

    @Size(max = 255, message = "size must be less than 255 character")
    private String shippingAddress;
}
