package com.example.unitprojectspring.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // This tells Spring to apply these web security rules
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for API endpoints as they are stateless or used for development
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                // 1. URL Authorization Rules
                .authorizeHttpRequests(auth -> auth
                        // Allow public access to registration, login, and static files
                        .requestMatchers("/register", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                        // Allow access to API endpoints for the team
                        .requestMatchers("/api/**").permitAll()
                        // Require authentication for other requests
                        .anyRequest().authenticated()
                )
                // 2. Custom Login Form Setup
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/projects", true)
                        .permitAll()
                )
                // 3. Logout Setup
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                // Enable HTTP Basic for easier API testing
                .httpBasic(org.springframework.security.config.Customizer.withDefaults());

        return http.build();
    }
}
