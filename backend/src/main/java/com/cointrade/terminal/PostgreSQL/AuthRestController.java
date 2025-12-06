// java
package com.cointrade.terminal.api;

import com.cointrade.terminal.PostgreSQL.User;
import com.cointrade.terminal.PostgreSQL.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthRestController {

    private final UserRepository userRepository;

    public AuthRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static class LoginRequest {
        public String username;
        public String password;
    }

    public static class LoginResponse {
        public String token;
        public String username;

        public LoginResponse(String token, String username) {
            this.token = token;
            this.username = username;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.username).orElse(null);
        if (user == null || !user.getPassword().equals(request.password)) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        String token = "demo-" + request.username + "-token";
        return ResponseEntity.ok(new LoginResponse(token, request.username));
    }
}
