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
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // Example: "BTC", "ETH", "USDT"
    @Column(nullable = false, length = 10)
    private String asset;

    // How much the user holds
    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal amount;

    // Total value in USD (optional)
    @Column(name = "usd_value", precision = 19, scale = 2)
    private BigDecimal usdValue;

    public Balance() {
    }

    public Balance(Long userId, String asset, BigDecimal amount, BigDecimal usdValue) {
        this.userId = userId;
        this.asset = asset;
        this.amount = amount;
        this.usdValue = usdValue;
    }

    // ---- Getters & Setters ----

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
