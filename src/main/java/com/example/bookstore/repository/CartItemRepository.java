package com.example.bookstore.repository;

import com.example.bookstore.model.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    //@EntityGraph(attributePaths = "book")
    @Query("SELECT DISTINCT ci FROM CartItem ci JOIN FETCH ci.book WHERE ci.shoppingCart.id = :cartId")
    List<CartItem> findAllByShoppingCartId(Long cartId);
}
