package com.cointrade.terminal.PostgreSQL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // points to login.html
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // points to dashboard.html
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // points to register.html
    }
}
