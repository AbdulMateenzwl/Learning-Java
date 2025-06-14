package com.example.demo.service.factory;

import org.springframework.stereotype.Component;

import com.example.demo.enums.UserRole;
import com.example.demo.service.strategy.getuser.AdminGetUserServiceStrategy;
import com.example.demo.service.strategy.getuser.ManagerGetUserServiceStrategy;
import com.example.demo.service.strategy.getuser.GetUserServiceStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetUserServiceStrategyFactory {
    private final AdminGetUserServiceStrategy adminUserServiceStrategy;
    private final ManagerGetUserServiceStrategy managerUserServiceStrategy;

    public GetUserServiceStrategy createStrategy(UserRole role) {
        return switch (role) {
            case ROLE_ADMIN -> adminUserServiceStrategy;
            case ROLE_MANAGER -> managerUserServiceStrategy;
            default -> throw new IllegalArgumentException("Unsupported role: " + role);
        };
    }
}
