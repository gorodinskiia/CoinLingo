package com.cointrade.terminal.PostgreSQL;

public class AdminStrategy implements UserStrategy {
    @Override
    public void accessRights() {
        System.out.println("Administrator has full access to the system.");
    }

    // Additional admin-specific actions
    public void deleteUser(User user) {
        System.out.println("Deleting user: " + user.getUsername());
        // implement deletion logic
    }

    public void resetUserPassword(User user) {
        System.out.println("Resetting password for user: " + user.getUsername());
        // implement reset logic
    }

    public void viewAllOrders() {
        System.out.println("Viewing all orders in the system");
        // implement viewing logic
    }
}
