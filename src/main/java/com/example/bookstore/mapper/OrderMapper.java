package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.order.OrderDto;
import com.example.bookstore.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);
}
