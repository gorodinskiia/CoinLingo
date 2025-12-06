package com.cointrade.terminal.PostgreSQL;

public class AdminInvoker {
    private Command command; //PART OF THE COMMAND PATTERN

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        if (command != null) {
            command.execute();
        }
    }
}
