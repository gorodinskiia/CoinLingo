package com.cointrade.terminal.PostgreSQL;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity // Makes a class a JPA entity or Database Table 
@Table(name = "orders") // Specifies the name of the database table to be used for mapping
public class Orders {

    @Id // Primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // primary key

    // The user placing the order
    @Column(nullable = false) // foreign key to Users table
    private Long userId;

    // buy or sell
    @Enumerated(EnumType.STRING) // Specifies that the enum should be persisted as a string in the database
    @Column(nullable = false)
    private OrderType type;

    // pending, filled, cancelled
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    // Which crypto (BTC, ETH, DOGE, etc.)
    @Column(nullable = false)
    private String asset;

    // How much crypto
    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

    // Price per unit
    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal price;

    // Total = quantity * price
    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal total;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Orders() {}

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
    
    // Enums for type and status
    public enum OrderType {
        BUY, SELL
    }

    public enum OrderStatus {
        PENDING, FILLED, CANCELLED
    }
}
