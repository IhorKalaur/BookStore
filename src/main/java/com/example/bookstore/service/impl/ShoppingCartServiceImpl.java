package com.example.bookstore.service.impl;

import com.example.bookstore.dto.cart.item.CartItemDto;
import com.example.bookstore.dto.cart.item.CreateCartItemRequestDto;
import com.example.bookstore.dto.cart.item.UpdateCartItemRequestDto;
import com.example.bookstore.dto.shopping.cart.ShoppingCartDto;
import com.example.bookstore.exceptions.EntityNotFoundException;
import com.example.bookstore.mapper.CartItemMapper;
import com.example.bookstore.mapper.ShoppingCartMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.service.ShoppingCartService;
import com.example.bookstore.service.UserService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserService userService;

    @Override
    public CartItemDto addCartItem(CreateCartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = userService.getShoppingCartForCurrentUser();

        CartItem cartItem = new CartItem();
        Book book = bookRepository.findById(requestDto.getBookId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Can't find book by id: " + requestDto.getBookId()));
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(requestDto.getQuantity());

        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    public ShoppingCartDto get() {
        ShoppingCart shoppingCart = userService.getShoppingCartForCurrentUser();

        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        shoppingCartDto.setCartItems(shoppingCart.getCartItems().stream()
                .map(cartItemMapper::toCartItemDtoWithoutShoppingCartId)
                .collect(Collectors.toSet()));

        return shoppingCartDto;
    }

    @Override
    public CartItemDto update(Long id, UpdateCartItemRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cartItem by id: " + id));
        cartItem.setQuantity(requestDto.getQuantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }
}
