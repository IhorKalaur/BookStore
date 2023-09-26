package com.example.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@SQLDelete(sql = "UPDATE orders SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted=false")
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(nullable = false,
            name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,
            name = "status")
    private Status status;

    @Column(nullable = false,
            name = "total")
    private BigDecimal total;

    @Column(nullable = false,
            name = "order_date")
    private LocalDateTime orderDate;

    @Column(nullable = false,
            name = "shipping_address")
    private String shippingAddress;

    @OneToMany(mappedBy = "order")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OrderItem> orderItems;

    @Column(nullable = false,
            name = "is_deleted")
    private boolean isDeleted;

    public enum Status {
        STATUS_COMPLETED,
        STATUS_CONFIRMED,
        STATUS_PENDING,
        STATUS_DELIVERED
    }
}
