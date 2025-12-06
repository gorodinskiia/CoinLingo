package com.cointrade.terminal.PostgreSQL;

public interface UserFactory {
    User createUser(String username, String email, String password);
}
