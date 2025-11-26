package com.cointrade.terminal.PostgreSQL;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class BalanceTest {

    @Test
    void testBalanceConstructorAndGetters() {
        User user = new User("testuser", "password", "test@example.com", 0);

        Balance balance = new Balance(
                user,
                "BTC",
                new BigDecimal("1.23456789"),
                new BigDecimal("50000.00")
        );

        assertEquals(user, balance.getUser());
        assertEquals("BTC", balance.getAsset());
        assertEquals(new BigDecimal("1.23456789"), balance.getAmount());
        assertEquals(new BigDecimal("50000.00"), balance.getUsdValue());
    }

    @Test
    void testSetters() {
        User user = new User("testuser", "password", "test@example.com", 0);
        Balance balance = new Balance();

        balance.setUser(user);
        balance.setAsset("ETH");
        balance.setAmount(new BigDecimal("10.5"));
        balance.setUsdValue(new BigDecimal("32000.00"));

        assertEquals(user, balance.getUser());
        assertEquals("ETH", balance.getAsset());
        assertEquals(new BigDecimal("10.5"), balance.getAmount());
        assertEquals(new BigDecimal("32000.00"), balance.getUsdValue());
    }

    @Test
    void testUserRelationshipChange() {
        User user1 = new User("user1", "pass", "u1@example.com", 1);
        User user2 = new User("user2", "pass", "u2@example.com", 2);

        Balance balance = new Balance();
        balance.setUser(user1);

        assertEquals(user1, balance.getUser());

        // Change user reference
        balance.setUser(user2);

        assertEquals(user2, balance.getUser());
    }

    @Test
    void testAmountPrecision() {
        Balance balance = new Balance();
        BigDecimal amount = new BigDecimal("12345.67890123");

        balance.setAmount(amount);

        assertEquals(amount, balance.getAmount());
        assertEquals(8, amount.scale()); // precision check
    }
}
