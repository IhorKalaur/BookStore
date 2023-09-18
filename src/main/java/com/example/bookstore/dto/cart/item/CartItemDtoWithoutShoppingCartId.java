package com.example.bookstore.dto.cart.item;

import lombok.Data;

@Data
public class CartItemDtoWithoutShoppingCartId {
    private Long id;
    
    private Long bookId;
    
    private String bookTitle;

    private Integer quantity;
}
