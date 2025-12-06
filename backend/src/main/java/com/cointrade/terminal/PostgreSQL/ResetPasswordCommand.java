package com.cointrade.terminal.PostgreSQL;

public class ResetPasswordCommand implements Command {
    private User targetUser;

    public ResetPasswordCommand(User targetUser) {
        this.targetUser = targetUser;
    }

    @Override
    public void execute() {
        targetUser.setPassword("defaultPassword123"); // Or generate new
        System.out.println("Reset password for: " + targetUser.getUsername());
    }
}
