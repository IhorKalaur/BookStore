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

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get shopping cart for current user",
            description = "to see which books are in shopping cart")
    public ShoppingCartDto get() {
        return shoppingCartService.get();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create new cart item",
            description = "add cart item to shopping cart for current user")
    public CartItemDto add(@RequestBody @Valid CreateCartItemRequestDto addRequestDto) {
        return shoppingCartService.add(addRequestDto);
    }

    @PutMapping("/cart-items/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a cart item",
            description = "update quantity of books in cart item")
    public CartItemDto update(@PathVariable Long id,
                              @RequestBody
                              @Valid UpdateCartItemRequestDto updateRequestDto) {
        return shoppingCartService.update(id, updateRequestDto);
    }

    @DeleteMapping("/cart-items/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a cart item",
            description = "delete cart item from shopping cart by id")
    void delete(@PathVariable Long id) {
        shoppingCartService.delete(id);
    }
}
