package com.example.bookstore.dto.shopping.cart;

import com.example.bookstore.dto.cart.item.CartItemDtoWithoutShoppingCartId;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;

    private Long userId;

    private String userName;

    private Set<CartItemDtoWithoutShoppingCartId> cartItems;
}
