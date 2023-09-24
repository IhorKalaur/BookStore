package com.example.bookstore.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class ValidCategoryIdsValidator implements
        ConstraintValidator<CategoryIdsValidator, Set<Long>> {
    @Override
    public boolean isValid(Set<Long> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        
        for (Long id : value) {
            if (id == null || id < 0) {
                return false;
            }
        }
        
        return true;
    }
}
