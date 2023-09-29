package com.example.bookstore.service.impl;

import com.example.bookstore.dto.order.CreateOrderShippingAddressRequestDto;
import com.example.bookstore.dto.order.OrderDto;
import com.example.bookstore.dto.order.OrderItemDto;
import com.example.bookstore.dto.order.UpdateOrderStatusRequestDto;
import com.example.bookstore.exceptions.EntityNotFoundException;
import com.example.bookstore.mapper.OrderItemMapper;
import com.example.bookstore.mapper.OrderMapper;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.repository.OrderItemRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderDto create(CreateOrderShippingAddressRequestDto createOrderRequestDto) {
        ShoppingCart shoppingCartForCurrentUser = userService.getShoppingCartForCurrentUser();

        Order order = new Order();
        order.setUser(userService.getCurrentUser());
        order.setStatus(Order.Status.STATUS_PENDING);
        order.setTotal(getTotalPrice(shoppingCartForCurrentUser));
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(createOrderRequestDto.getShippingAddress());
        order = orderRepository.save(order);

        Set<OrderItem> orderItems = mapToOrderItems(shoppingCartForCurrentUser
                .getCartItems(), order);
        orderItemRepository.saveAll(orderItems);

        OrderDto orderDto = orderMapper.toDto(order);

        orderDto.setOrderItems(orderItems.stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet()));

        return orderDto;
    }

    private Set<OrderItem> mapToOrderItems(Set<CartItem> cartItems, Order order) {
        return cartItems.stream()
                .map(cartItem -> toOrderItem(cartItem, order))
                .collect(Collectors.toSet());
    }

    private OrderItem toOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        orderItem.setOrder(order);
        return orderItem;
    }

    @Override
    public List<OrderDto> getAll(Pageable pageable) {
        Long id = userService.getCurrentUser().getId();
        List<Order> allOrdersByUserId = orderRepository.findAllByUserId(id);
        return allOrdersByUserId.stream()
                .map(order -> {
                    OrderDto orderDto = orderMapper.toDto(order);
                    Set<OrderItemDto> orderItemDtos = order.getOrderItems().stream()
                            .map(orderItemMapper::toDto)
                            .collect(Collectors.toSet());
                    orderDto.setOrderItems(orderItemDtos);

                    return orderDto;
                })
                .toList();
    }

    @Override
    @Transactional
    public OrderDto updateStatus(Long id, UpdateOrderStatusRequestDto orderStatusRequestDto) {
        Order orderFromDb = orderRepository.findOrderById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by id " + id));
        orderFromDb.setStatus(orderStatusRequestDto.getStatus());
        Order savedOrder = orderRepository.save(orderFromDb);
        OrderDto orderDto = orderMapper.toDto(savedOrder);
        Set<OrderItem> orderItems = savedOrder.getOrderItems();
        orderDto.setOrderItems(orderItems.stream()
                        .map(orderItemMapper::toDto)
                .collect(Collectors.toSet()));

        return orderDto;
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long orderId, Pageable pageable) {
        Order orderFromDb = getOrderForCurrentUser(orderId);
        return orderFromDb.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getByItemIdAndOrderId(Long itemId, Long orderId) {
        getOrderForCurrentUser(orderId);
        return orderItemRepository
                .findOrderItemByIdAndOrderId(itemId, orderId)
                .map(orderItemMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find Order Item by id: " + itemId
                ));
    }

    private Order getOrderForCurrentUser(Long orderId) {
        Long currentUsersId = userService.getCurrentUser().getId();
        Order orderFromDb = orderRepository.findOrderById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by id " + orderId));
        if (!orderFromDb.getUser().getId().equals(currentUsersId)) {
            throw new EntityNotFoundException("Can't find order by id " + orderId);
        }
        return orderFromDb;
    }

    private BigDecimal getTotalPrice(ShoppingCart shoppingCart) {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            totalPrice = totalPrice.add(cartItem.getBook()
                    .getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
        }
        return totalPrice;
    }
}
