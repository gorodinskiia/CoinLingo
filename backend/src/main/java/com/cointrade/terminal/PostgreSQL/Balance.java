package com.cointrade.terminal.PostgreSQL;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "balances")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User this balance belongs to
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Example: "BTC", "ETH", "USDT"
    @Column(nullable = false, length = 10)
    private String asset;

    // How much the user holds
    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal amount;

    // Total value in USD (optional)
    @Column(name = "usd_value", precision = 19, scale = 2)
    private BigDecimal usdValue;

    public Balance() {}

    public Balance(User user, String asset, BigDecimal amount, BigDecimal usdValue) {
        this.user = user;
        this.asset = asset;
        this.amount = amount;
        this.usdValue = usdValue;
    }

    // ---- Getters & Setters ----

    public Long getId() {
        return id;
    }

    public User getUser() {      // FIXED
        return user;
    }

    public void setUser(User user) {   // FIXED
        this.user = user;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUsdValue() {
        return usdValue;
    }

    public void setUsdValue(BigDecimal usdValue) {
        this.usdValue = usdValue;
    }
}
