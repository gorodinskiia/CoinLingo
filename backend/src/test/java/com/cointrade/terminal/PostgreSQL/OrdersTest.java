package com.cointrade.terminal.PostgreSQL;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrdersTest {

    @Test
    void testOrderDefaults() {
        Orders order = new Orders();

        assertNull(order.getId());
        assertEquals(Orders.OrderStatus.PENDING, order.getStatus());
        assertNotNull(order.getCreatedAt());
    }

    @Test
    void testSettersAndGetters() {
        Orders order = new Orders();

        order.setId(1L);
        order.setUserId(42L);
        order.setType(Orders.OrderType.BUY);
        order.setStatus(Orders.OrderStatus.FILLED);
        order.setAsset("BTC");
        order.setQuantity(new BigDecimal("0.5"));
        order.setPrice(new BigDecimal("30000.00"));
        order.setTotal(new BigDecimal("15000.00"));
        LocalDateTime timestamp = LocalDateTime.now();
        order.setCreatedAt(timestamp);

        assertEquals(1L, order.getId());
        assertEquals(42L, order.getUserId());
        assertEquals(Orders.OrderType.BUY, order.getType());
        assertEquals(Orders.OrderStatus.FILLED, order.getStatus());
        assertEquals("BTC", order.getAsset());
        assertEquals(new BigDecimal("0.5"), order.getQuantity());
        assertEquals(new BigDecimal("30000.00"), order.getPrice());
        assertEquals(new BigDecimal("15000.00"), order.getTotal());
        assertEquals(timestamp, order.getCreatedAt());
    }

    @Test
    void testOrderTotalCalculation() {
        Orders order = new Orders();

        BigDecimal qty = new BigDecimal("1.25");
        BigDecimal price = new BigDecimal("20000.00");
        BigDecimal expectedTotal = qty.multiply(price);

        order.setQuantity(qty);
        order.setPrice(price);
        order.setTotal(expectedTotal);

        assertEquals(expectedTotal, order.getTotal());
    }
}
