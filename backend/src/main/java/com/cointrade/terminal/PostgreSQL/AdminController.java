package com.cointrade.terminal.PostgreSQL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.cointrade.terminal.PostgreSQL.User;
import com.cointrade.terminal.PostgreSQL.UserRepository;
import com.cointrade.terminal.PostgreSQL.ResetPasswordCommand;
import com.cointrade.terminal.PostgreSQL.AdminInvoker;
import com.cointrade.terminal.PostgreSQL.Command;
import com.cointrade.terminal.PostgreSQL.DeleteUserCommand;
import com.cointrade.terminal.PostgreSQL.ViewAllOrdersCommand;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // User Management
    @PostMapping("/admin/manageUsers")
    public String manageUsers(Model model) {
        // You can add user list to model if you want to display it
        model.addAttribute("users", userRepository.findAll());
        System.out.println("Admin managing users...");
        return "adminDashboard"; // stay on dashboard
    }

    // Reset User Password
    @PostMapping("/admin/resetPassword")
    public String resetPassword(@RequestParam String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            ResetPasswordCommand command = new ResetPasswordCommand(user);
            AdminInvoker invoker = new AdminInvoker();
            invoker.setCommand(command);
            invoker.executeCommand();
        });
        return "adminDashboard";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            DeleteUserCommand command = new DeleteUserCommand(user, userRepository);
            AdminInvoker invoker = new AdminInvoker();
            invoker.setCommand(command);
            invoker.executeCommand();
        });
        return "adminDashboard";
    }

    // View All Orders
    @PostMapping("/admin/viewOrders")
    public String viewOrders() {
        ViewAllOrdersCommand command = new ViewAllOrdersCommand();
        AdminInvoker invoker = new AdminInvoker();
        invoker.setCommand(command);
        invoker.executeCommand();
        System.out.println("Viewing all orders...");
        // Implement order retrieval logic here
        return "adminDashboard";
    }

    // System Settings
    @PostMapping("/admin/systemSettings")
    public String systemSettings() {
        System.out.println("Accessing system settings...");
        // Implement system settings logic here
        return "adminDashboard";
    }

    // View Logs
    @PostMapping("/admin/viewLogs")
    public String viewLogs() {
        System.out.println("Viewing audit logs...");
        // Implement log viewing logic here
        return "adminDashboard";
    }
}
