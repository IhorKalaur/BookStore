package com.example.bookstore.dto.cart.item;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateCartItemRequestDto {
    @Positive(message = "only positive digits allowed")
    private Integer quantity;
}
