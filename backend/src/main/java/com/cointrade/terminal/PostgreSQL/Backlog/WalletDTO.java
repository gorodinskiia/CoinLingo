package com.cointrade.terminal.PostgreSQL;

import java.math.BigDecimal;
import java.util.Map;

public class WalletDTO {
    private Map<String, BigDecimal> balances;
    private BigDecimal portfolioValue;
    private BigDecimal profitLoss;
    private Double profitLossPercentage;

    public WalletDTO() {}

    public WalletDTO(Map<String, BigDecimal> balances, BigDecimal portfolioValue,
                     BigDecimal profitLoss, Double profitLossPercentage) {
        this.balances = balances;
        this.portfolioValue = portfolioValue;
        this.profitLoss = profitLoss;
        this.profitLossPercentage = profitLossPercentage;
    }

    // Getters and Setters
    public Map<String, BigDecimal> getBalances() {
        return balances;
    }

    public void setBalances(Map<String, BigDecimal> balances) {
        this.balances = balances;
    }

    public BigDecimal getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(BigDecimal portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public BigDecimal getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(BigDecimal profitLoss) {
        this.profitLoss = profitLoss;
    }

    public Double getProfitLossPercentage() {
        return profitLossPercentage;
    }

    public void setProfitLossPercentage(Double profitLossPercentage) {
        this.profitLossPercentage = profitLossPercentage;
    }
}

