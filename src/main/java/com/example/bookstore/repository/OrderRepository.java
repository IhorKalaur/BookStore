package com.example.bookstore.repository;

import com.example.bookstore.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.book"},
            type = EntityGraph.EntityGraphType.LOAD)
    List<Order> findAllByUserId(Long userId);

    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.book"},
            type = EntityGraph.EntityGraphType.LOAD)
    Optional<Order> findOrderById(Long id);
}
