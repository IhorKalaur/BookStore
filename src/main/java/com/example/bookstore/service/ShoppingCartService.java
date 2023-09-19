package com.example.bookstore.service;

import com.example.bookstore.dto.cart.item.CartItemDto;
import com.example.bookstore.dto.cart.item.CreateCartItemRequestDto;
import com.example.bookstore.dto.cart.item.UpdateCartItemRequestDto;
import com.example.bookstore.dto.shopping.cart.ShoppingCartDto;

public interface ShoppingCartService {
    CartItemDto addCartItem(CreateCartItemRequestDto requestDto);

    ShoppingCartDto get();

    CartItemDto updateCartItem(Long id, UpdateCartItemRequestDto requestDto);

    void deleteCartItem(Long id);
}
