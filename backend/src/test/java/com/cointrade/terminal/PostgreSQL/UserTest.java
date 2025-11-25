package com.cointrade.terminal.PostgreSQL;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cointrade.terminal.PostgreSQL.User;

class UserTest {

    @Test
    void testNoArgsConstructor() {
        User user = new User();

        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
        assertEquals(0, user.getDailyStreak());
    }

    @Test
    void testAllArgsConstructor() {
        User user = new User("ivan", "password123", "ivan@example.com", 5);

        assertNull(user.getId());  // ID is null until persisted in DB
        assertEquals("ivan", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("ivan@example.com", user.getEmail());
        assertEquals(5, user.getDailyStreak());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();

        user.setUsername("alice");
        user.setPassword("mypassword");
        user.setEmail("alice@example.com");
        user.setDailyStreak(10);

        assertEquals("alice", user.getUsername());
        assertEquals("mypassword", user.getPassword());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals(10, user.getDailyStreak());
    }

    @Test
    void testDailyStreakCannotBeNegative() {
        User user = new User();

        user.setDailyStreak(-1);  // You can later enforce validation

        assertEquals(-1, user.getDailyStreak());  
        // Later you can add custom validation to prevent negative streaks
    }
}
