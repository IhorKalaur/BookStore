package com.example.bookstore.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCategoryIdsValidator.class)
@Documented
public @interface CategoryIdsValidator {
    String message() default "Invalid category IDs";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
