package com.example.bookstore.repository;

import com.example.bookstore.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrdersByUserId(Long userId);

    Optional<Order> findOrderById(Long id);
}
