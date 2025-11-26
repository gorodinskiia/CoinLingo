package com.cointrade.terminal.PostgreSQL;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User who placed the order
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // BUY or SELL
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType type;

    // PENDING, FILLED, CANCELLED
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    // Example: BTC, ETH, SOL
    @Column(nullable = false)
    private String asset;

    // Amount of crypto
    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

    // Price per unit
    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal price;

    // quantity × price
    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal total;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Orders() {}

    public Orders(User user, OrderType type, String asset, BigDecimal quantity, BigDecimal price) {
        this.user = user;
        this.type = type;
        this.asset = asset;
        this.quantity = quantity;
        this.price = price;

        // SAFE total calculation
        this.total = (quantity != null && price != null)
            ? quantity.multiply(price)
            : BigDecimal.ZERO;

        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }


    // ---------- Getters & Setters ----------

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ---------- ENUMS ----------
    public enum OrderType {
        BUY,
        SELL
    }

    public enum OrderStatus {
        PENDING,
        FILLED,
        CANCELLED
    }
}
