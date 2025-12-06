package com.cointrade.terminal.PostgreSQL;

public class NormalUserStrategy implements UserStrategy {
    @Override
    public void accessRights() {
        System.out.println("Normal user has limited access to the system.");
    }
}
