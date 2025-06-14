package com.example.demo.config;

import com.example.demo.enums.UserRole;
import com.example.demo.entity.User;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserContext {
    public UUID getUserId() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return user.getUuid();
    }

    public UserRole getRole() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return user.getRole();
    }
}

