package com.example.bookstore.dto.cart.item;

import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    private Long bookId;

    private Integer quantity;
}
