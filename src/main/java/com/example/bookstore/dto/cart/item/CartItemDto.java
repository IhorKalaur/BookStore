package com.example.bookstore.dto.cart.item;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;

    private Long shoppingCartId;

    private Long bookId;

    private Integer quantity;
}
