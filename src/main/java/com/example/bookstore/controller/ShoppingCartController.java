package com.example.bookstore.controller;

import com.example.bookstore.dto.cart.item.CartItemDto;
import com.example.bookstore.dto.cart.item.CreateCartItemRequestDto;
import com.example.bookstore.dto.cart.item.UpdateCartItemRequestDto;
import com.example.bookstore.dto.shopping.cart.ShoppingCartDto;
import com.example.bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PostMapping
    public CartItemDto create(@RequestBody CreateCartItemRequestDto cartItemRequestDto) {
        return shoppingCartService.addCartItem(cartItemRequestDto);
    }

    @GetMapping
    public ShoppingCartDto get() {
        return shoppingCartService.get();
    }

    @PutMapping("/cart-items/{id}")
    public CartItemDto updateCartItem(@PathVariable Long id,
                                      @RequestBody UpdateCartItemRequestDto requestDto) {
        return shoppingCartService.updateCartItem(id, requestDto);
    }

    @DeleteMapping("/cart-items/{id}")
    void deleteCartItem(@PathVariable Long id) {
        shoppingCartService.deleteCartItem(id);
    }
}
