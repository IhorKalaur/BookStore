package com.example.bookstore.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {
    private static final String ISBN_PATTERN = "^(?:\\d{9}[\\dXx]|\\d{13})$";

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        return isbn.matches(ISBN_PATTERN);
    }
}
