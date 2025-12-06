package com.cointrade.terminal.PostgreSQL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

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
            System.out.println("Resetting password for: " + user.getUsername());
            // Implement password reset logic here
        });
        return "adminDashboard";
    }

    // View All Orders
    @PostMapping("/admin/viewOrders")
    public String viewOrders() {
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
