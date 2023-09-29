package com.example.bookstore.service;

import com.example.bookstore.dto.order.CreateOrderShippingAddressRequestDto;
import com.example.bookstore.dto.order.OrderDto;
import com.example.bookstore.dto.order.OrderItemDto;
import com.example.bookstore.dto.order.UpdateOrderStatusRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto create(CreateOrderShippingAddressRequestDto createOrderRequestDto);

    List<OrderDto> getAll(Pageable pageable);

    OrderDto updateStatus(Long id, UpdateOrderStatusRequestDto orderStatusRequestDto);

    List<OrderItemDto> getOrderItems(Long orderId, Pageable pageable);

    OrderItemDto getByItemIdAndOrderId(Long itemId, Long orderId);
}
