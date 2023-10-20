package com.example.bookstore.controller;

import com.example.bookstore.dto.order.CreateOrderShippingAddressRequestDto;
import com.example.bookstore.dto.order.OrderDto;
import com.example.bookstore.dto.order.OrderItemDto;
import com.example.bookstore.dto.order.UpdateOrderStatusRequestDto;
import com.example.bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for maneging orders")
@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @Operation(
            summary = "Get all order history",
            description = "Get a list of order history. Only for users"
    )
    @PreAuthorize("hasRole('USER')")
    public List<OrderDto> getAll(Pageable pageable) {
        return orderService.getAll(pageable);
    }

    @GetMapping(value = "/{orderId}/items")
    @Operation(
            summary = "Get all order items for a specific order",
            description = "Get a list of all order items in for a specific order. Only for users"
    )
    @PreAuthorize("hasRole('USER')")
    public List<OrderItemDto> getOrderItems(@PathVariable Long orderId,
                                            Pageable pageable) {
        return orderService.getOrderItems(orderId, pageable);
    }

    @GetMapping(value = "/{orderId}/items/{itemId}")
    @Operation(
            summary = "Get a specific order item for a specific order",
            description = "Get a specific order item for a specific order for current user."
                    + " Only for users"
    )
    @PreAuthorize("hasRole('USER')")
    public OrderItemDto getByItemIdAndOrderId(@PathVariable Long itemId,
                                              @PathVariable Long orderId) {
        return orderService.getByItemIdAndOrderId(itemId,orderId);
    }

    @PostMapping
    @Operation(
            summary = "Create an order",
            description = "Set shipping address for orders and create order"
    )
    @PreAuthorize("hasRole('USER')")
    public OrderDto create(
            @RequestBody @Valid CreateOrderShippingAddressRequestDto shippingAddressRequestDto) {
        return orderService.create(shippingAddressRequestDto);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update order status",
            description = "Update the status of a specific order"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDto updateStatus(@PathVariable Long id,
                                 @RequestBody UpdateOrderStatusRequestDto statusRequestDto) {
        return orderService.updateStatus(id, statusRequestDto);
    }
}
