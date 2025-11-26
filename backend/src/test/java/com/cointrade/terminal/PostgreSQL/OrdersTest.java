package com.cointrade.terminal.PostgreSQL;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrdersTest {

    @Test
    void testConstructorAndGetters() {
        User user = new User("testuser", "pass", "test@example.com", 1);

        BigDecimal quantity = new BigDecimal("2.5");
        BigDecimal price = new BigDecimal("100.00");

        Orders order = new Orders(
                user,
                Orders.OrderType.BUY,
                "BTC",
                quantity,
                price
        );

        assertEquals(user, order.getUser());
        assertEquals(Orders.OrderType.BUY, order.getType());
        assertEquals("BTC", order.getAsset());
        assertEquals(quantity, order.getQuantity());
        assertEquals(price, order.getPrice());
        //assertEquals(new BigDecimal("250.00"), order.getTotal()); // 2.5 × 100.00
        assertEquals(Orders.OrderStatus.PENDING, order.getStatus());
        assertNotNull(order.getCreatedAt());
    }

    @Test
    void testSetters() {
        Orders order = new Orders();
        User user = new User("alice", "pass", "alice@example.com", 4);

        order.setUser(user);
        order.setType(Orders.OrderType.SELL);
        order.setStatus(Orders.OrderStatus.FILLED);
        order.setAsset("ETH");
        order.setQuantity(new BigDecimal("10.12345678"));
        order.setPrice(new BigDecimal("2500.50"));
        order.setTotal(new BigDecimal("25300.00"));
        LocalDateTime now = LocalDateTime.now();
        order.setCreatedAt(now);

        assertEquals(user, order.getUser());
        assertEquals(Orders.OrderType.SELL, order.getType());
        assertEquals(Orders.OrderStatus.FILLED, order.getStatus());
        assertEquals("ETH", order.getAsset());
        assertEquals(new BigDecimal("10.12345678"), order.getQuantity());
        assertEquals(new BigDecimal("2500.50"), order.getPrice());
        assertEquals(new BigDecimal("25300.00"), order.getTotal());
        assertEquals(now, order.getCreatedAt());
    }

    @Test
    void testTotalCalculationSafety() {
        User user = new User("bob", "pass", "bob@example.com", 2);

        Orders order = new Orders(
                user,
                Orders.OrderType.BUY,
                "SOL",
                null,         // quantity missing
                new BigDecimal("50.00")
        );

        assertEquals(BigDecimal.ZERO, order.getTotal());
    }

    @Test
    void testEnumValues() {
        assertEquals("BUY", Orders.OrderType.BUY.name());
        assertEquals("SELL", Orders.OrderType.SELL.name());

        assertEquals("PENDING", Orders.OrderStatus.PENDING.name());
        assertEquals("FILLED", Orders.OrderStatus.FILLED.name());
        assertEquals("CANCELLED", Orders.OrderStatus.CANCELLED.name());
    }

    @Test
    void testCreatedAtIsAssigned() {
        User user = new User("charlie", "pass", "charlie@example.com", 3);

        Orders order = new Orders(
                user,
                Orders.OrderType.SELL,
                "ADA",
                new BigDecimal("100"),
                new BigDecimal("0.50")
        );

        assertNotNull(order.getCreatedAt());
    }
}
