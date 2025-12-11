package com.cointrade.terminal.PostgreSQL;

import java.math.BigDecimal;

public class TradeRequest {
    private String side; // "buy" or "sell"
    private String asset; // "BTC", "ETH", "XRP"
    private BigDecimal amount;
    private BigDecimal price;

    public TradeRequest() {}

    public TradeRequest(String side, String asset, BigDecimal amount, BigDecimal price) {
        this.side = side;
        this.asset = asset;
        this.amount = amount;
        this.price = price;
    }

    // Getters and Setters
    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

