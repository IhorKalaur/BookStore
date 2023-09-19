package com.example.bookstore.controller;

import com.example.bookstore.dto.cart.item.CartItemDto;
import com.example.bookstore.dto.cart.item.CreateCartItemRequestDto;
import com.example.bookstore.dto.cart.item.UpdateCartItemRequestDto;
import com.example.bookstore.dto.shopping.cart.ShoppingCartDto;
import com.example.bookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ShoppingCart management", description = "Endpoints for maneging ShoppingCarts")
@RestController
@RequestMapping(value = "/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get shopping cart for current user",
            description = "to see which books are in shopping cart")
    public ShoppingCartDto get() {
        return shoppingCartService.get();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "create new cart item",
            description = "add cart item to shopping cart for current user")
    public CartItemDto addCartItem(@RequestBody @Valid CreateCartItemRequestDto addRequestDto) {
        return shoppingCartService.addCartItem(addRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/cart-items/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "update a cart item",
            description = "update quantity of books in cart item")
    public CartItemDto updateCartItem(@PathVariable Long id,
                                      @RequestBody
                                      @Valid UpdateCartItemRequestDto updateRequestDto) {
        return shoppingCartService.updateCartItem(id, updateRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/cart-items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete a cart item",
            description = "delete cart item from shopping cart by id")
    void deleteCartItem(@PathVariable Long id) {
        shoppingCartService.deleteCartItem(id);
    }
}
