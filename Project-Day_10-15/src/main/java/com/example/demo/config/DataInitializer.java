package com.example.demo.config;

import com.example.demo.ENUM.UserRole;
import com.example.demo.Entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@taskapp.com").isEmpty()) {
            User admin = User.builder()
                    .uuid(UUID.randomUUID())
                    .email("admin@taskapp.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.ROLE_ADMIN)
                    .restricted(false)
                    .build();

            userRepository.save(admin);
            System.out.println("Admin user created");
        } else {
            System.out.println("Admin user already exists");
        }
        if (userRepository.findByEmail("manager@taskapp.com").isEmpty()) {
            User admin = User.builder()
                    .uuid(UUID.randomUUID())
                    .email("manager@taskapp.com")
                    .password(passwordEncoder.encode("man123"))
                    .role(UserRole.ROLE_MANAGER)
                    .restricted(false)
                    .build();

            userRepository.save(admin);
            System.out.println("Manager user created");
        } else {
            System.out.println("Manager user already exists");
        }
    }
}
