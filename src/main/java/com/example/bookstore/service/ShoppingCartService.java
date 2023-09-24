package com.example.bookstore.service;

import com.example.bookstore.dto.cart.item.CartItemDto;
import com.example.bookstore.dto.cart.item.CreateCartItemRequestDto;
import com.example.bookstore.dto.cart.item.UpdateCartItemRequestDto;
import com.example.bookstore.dto.shopping.cart.ShoppingCartDto;

public interface ShoppingCartService {
    CartItemDto add(CreateCartItemRequestDto requestDto);

    ShoppingCartDto get();

    CartItemDto update(Long id, UpdateCartItemRequestDto requestDto);

    void delete(Long id);
}
