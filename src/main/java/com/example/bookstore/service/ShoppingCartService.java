package com.example.bookstore.service;

import com.example.bookstore.dto.cart.item.CartItemDto;
import com.example.bookstore.dto.cart.item.CreateCartItemRequestDto;
import com.example.bookstore.dto.shopping.cart.ShoppingCartDto;

public interface ShoppingCartService {
    CartItemDto save(CreateCartItemRequestDto requestDto);

    ShoppingCartDto get();


}
