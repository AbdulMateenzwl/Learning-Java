package com.example.demo.service.factory;

import org.springframework.stereotype.Component;

import com.example.demo.enums.UserRole;
import com.example.demo.service.strategy.user.AdminService;
import com.example.demo.service.strategy.user.ManagerService;
import com.example.demo.service.strategy.user.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserServiceFactory {
    private final AdminService adminService;
    private final ManagerService managerService;

    public UserService get(UserRole role) {
        return switch (role) {
            case ROLE_ADMIN -> adminService;
            case ROLE_MANAGER -> managerService;
            default -> throw new IllegalArgumentException("Unsupported role: " + role);
        };
    }
}
