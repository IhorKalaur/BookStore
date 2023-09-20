package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.shopping.cart.ShoppingCartDto;
import com.example.bookstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    @Mapping(source = "user.email", target = "userName")
    @Mapping(source = "user.id", target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
