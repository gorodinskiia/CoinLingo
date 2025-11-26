package com.cointrade.terminal.PostgreSQL;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BalanceTest {

    @Test
    void testConstructorSetsFields() {
        Balance balance = new Balance(
                5L,
                "ETH",
                new BigDecimal("2.25"),
                new BigDecimal("4500.00")
        );

        assertEquals(5L, balance.getUserId());
        assertEquals("ETH", balance.getAsset());
        assertEquals(new BigDecimal("2.25"), balance.getAmount());
        assertEquals(new BigDecimal("4500.00"), balance.getUsdValue());
    }

    @Test
    void testSettersAndGetters() {
        Balance balance = new Balance();

        balance.setUserId(20L);
        balance.setAsset("BTC");
        balance.setAmount(new BigDecimal("0.75"));
        balance.setUsdValue(new BigDecimal("30000.00"));

        assertEquals(20L, balance.getUserId());
        assertEquals("BTC", balance.getAsset());
        assertEquals(new BigDecimal("0.75"), balance.getAmount());
        assertEquals(new BigDecimal("30000.00"), balance.getUsdValue());
    }

    @Test
    void testIdIsInitiallyNull() {
        Balance balance = new Balance();
        assertNull(balance.getId());  // JPA assigns this — not in a unit test
    }
}
