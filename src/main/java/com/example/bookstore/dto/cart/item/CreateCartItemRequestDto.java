package com.example.bookstore.dto.cart.item;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    @Positive(message = "only positive digits allowed")
    private Long bookId;
    @Positive(message = "only positive digits allowed")
    private Integer quantity;
}
