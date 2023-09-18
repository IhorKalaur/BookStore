package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.cart.item.CartItemDto;
import com.example.bookstore.dto.cart.item.CartItemDtoWithoutShoppingCartId;
import com.example.bookstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(source = "shoppingCart.id", target = "shoppingCartId")
    @Mapping(source = "book.id", target = "bookId")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDtoWithoutShoppingCartId toCartItemDtoWithoutShoppingCartId(CartItem cartItem);
}
