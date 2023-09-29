package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.order.OrderItemDto;
import com.example.bookstore.model.OrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {

    OrderItemDto toDto(OrderItem item);

    @AfterMapping
    default void setBookId(@MappingTarget OrderItemDto orderItemDto, OrderItem orderItem) {
        orderItemDto.setBookId(orderItem.getBook().getId());
    }
}
