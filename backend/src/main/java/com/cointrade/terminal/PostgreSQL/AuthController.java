package com.cointrade.terminal.PostgreSQL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AuthController {

    private final UserRepository userRepository;

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
        return "redirect:/dashboard";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute User user) {
        //logger.info("Register called with user: {}", user.getUsername());
        // save user

        //userRepository.save(user);

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
}
