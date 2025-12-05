package com.cointrade.terminal.PostgreSQL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Returns login.html
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // Returns dashboard.html
    }
}
