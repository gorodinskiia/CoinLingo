package com.cointrade.terminal.PostgreSQL;

public class ConcreteUserFactory implements UserFactory {

    @Override
    public User createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        // Assign strategy based on username or role
        if (username.equalsIgnoreCase("admin")) {
            user.setStrategy(new AdminStrategy());
        } else {
            user.setStrategy(new NormalUserStrategy());
        }

        return user;
    }
}
