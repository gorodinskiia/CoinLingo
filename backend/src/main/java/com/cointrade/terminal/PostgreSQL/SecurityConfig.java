// package com.cointrade.terminal.PostgreSQL;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.crypto.password.NoOpPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class SecurityConfig {

//     private final CustomUserDetailsService userDetailsService;

//     public SecurityConfig(CustomUserDetailsService userDetailsService) {
//         this.userDetailsService = userDetailsService;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         // WARNING: Only for testing! Passwords are stored in plain text.
//         return NoOpPasswordEncoder.getInstance();
//     }

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable())  // disable CSRF for testing
//             .authorizeHttpRequests(auth -> auth
//                     .requestMatchers("/login", "/register", "/css/**").permitAll()
//                     .anyRequest().authenticated()
//             )
//             .formLogin(form -> form
//                     .loginPage("/login")
//                     .defaultSuccessUrl("/dashboard", true)
//                     .permitAll()
//             )
//             .logout(logout -> logout
//                     .logoutUrl("/logout")
//                     .logoutSuccessUrl("/login?logout")
//                     .permitAll()
//             );

//         return http.build();
//     }
// }
