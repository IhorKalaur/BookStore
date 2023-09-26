package com.example.bookstore.controller;

import com.example.bookstore.dto.order.CreateOrderShippingAddressRequestDto;
import com.example.bookstore.dto.order.OrderDto;
import com.example.bookstore.dto.order.OrderItemDto;
import com.example.bookstore.dto.order.UpdateOrderStatusRequestDto;
import com.example.bookstore.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderDto create(
            @RequestBody CreateOrderShippingAddressRequestDto shippingAddressRequestDto) {
        return orderService.create(shippingAddressRequestDto);
    }

    @GetMapping
    public List<OrderDto> getAll(Pageable pageable){
        return orderService.getAll(pageable);
    }

    @PatchMapping("/{id}")
    public OrderDto updateStatus(@PathVariable Long id,
                                 @RequestBody UpdateOrderStatusRequestDto statusRequestDto) {
        return orderService.updateStatus(id, statusRequestDto);
    }

    @GetMapping(value = "/{orderId}/items")
    public List<OrderItemDto> getOrderItems(@PathVariable Long orderId,
                                            Pageable pageable) {
        return orderService.getOrderItems(orderId, pageable);
    }

    @GetMapping(value = "/{orderId}/items/{itemId}")
    public OrderItemDto getByItemIdAndOrderId(@PathVariable Long itemId,
                                              @PathVariable Long orderId){
        return orderService.getByItemIdAndOrderId(itemId,orderId);
    }
}
