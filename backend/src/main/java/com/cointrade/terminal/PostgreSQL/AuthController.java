package com.cointrade.terminal.PostgreSQL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Optional;
import com.cointrade.terminal.PostgreSQL.User;
import com.cointrade.terminal.PostgreSQL.UserRepository;
import com.cointrade.terminal.PostgreSQL.AdminStrategy;
import com.cointrade.terminal.PostgreSQL.NormalUserStrategy;
import org.springframework.ui.Model;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // For testing: ignore username/password, always redirect
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return "redirect:/error"; // user not found
        }

        User user = optionalUser.get();

        if (user.getUsername().equalsIgnoreCase("admin")) 
        {
            logger.info("Admin user login");
            user.setStrategy(new AdminStrategy());
        } 
        else 
        {
            logger.info("Normal user login");
            user.setStrategy(new NormalUserStrategy());
        }

        user.performAccessRights();

        if (user.getStrategy() instanceof AdminStrategy) {
            logger.info("Admin user login");
            return "redirect:/adminDashboard";
        }

        logger.info("Normal user login");
        return "redirect:/adminDashboard";

    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute User user) {
        //logger.info("Register called with user: {}", user.getUsername());
        // save user
         try {
            userRepository.save(user); // try to save
        } catch (DataIntegrityViolationException e) {
            // If email already exists or any DB constraint fails, go to /error
            return "redirect:/error";
        }

        return "redirect:/login";
    }


    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error"; // make sure you have src/main/resources/templates/error.html
    }


    @GetMapping("/adminDashboard")
    public String adminDashboard() {
        return "adminDashboard";
    }
    

}
