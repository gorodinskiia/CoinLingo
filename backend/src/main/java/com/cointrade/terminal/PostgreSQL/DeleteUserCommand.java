package com.cointrade.terminal.PostgreSQL;

public class DeleteUserCommand implements Command {
    private User targetUser;
    private UserRepository userRepository;

    public DeleteUserCommand(User targetUser, UserRepository userRepository) {
        this.targetUser = targetUser;
        this.userRepository = userRepository;
    }

    @Override
    public void execute() {
        userRepository.delete(targetUser);
        System.out.println("Deleted user: " + targetUser.getUsername());
    }
}
