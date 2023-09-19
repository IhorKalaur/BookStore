package com.example.bookstore.dto.cart.item;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    @Positive
    private Long bookId;
    @Positive
    private Integer quantity;
}
