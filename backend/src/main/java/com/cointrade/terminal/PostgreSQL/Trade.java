package com.cointrade.terminal.PostgreSQL;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // primary key

    @Column(nullable = false)
    private String symbol;    // e.g. BTC

    @Column(nullable = false)
    private Double quantity;  // e.g. 0.5 BTC

    @Column(nullable = false)
    private Double price;     // e.g. 45000.00 USD

    @Column(nullable = false)
    private LocalDateTime timestamp; // when the trade happened

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // who made the trade

    public Trade() {}

    public Trade(String symbol, Double quantity, Double price, User user) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.user = user;
        this.timestamp = LocalDateTime.now();
    }

    // ---- Correct Getters / Setters ----

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
