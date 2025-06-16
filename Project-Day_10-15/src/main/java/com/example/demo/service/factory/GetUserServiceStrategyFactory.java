package com.example.demo.service.factory;

import org.springframework.stereotype.Component;

import com.example.demo.enums.UserRole;
import com.example.demo.service.strategy.getuser.AdminGetUserServiceStrategy;
import com.example.demo.service.strategy.getuser.ManagerGetUserServiceStrategy;
import com.example.demo.service.strategy.getuser.GetUserServiceStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

//don't add verb like GET in class name
//don't be too verbose (no need of strategy word in class name as Factory is already present) 
//the name can be: UserServiceFactory
public class GetUserServiceStrategyFactory {
    private final AdminGetUserServiceStrategy adminUserServiceStrategy;
    private final ManagerGetUserServiceStrategy managerUserServiceStrategy;

    //the method signature should be "UserService get(UserRole role) {}"
    public GetUserServiceStrategy createStrategy(UserRole role) {
        return switch (role) {
            case ROLE_ADMIN -> adminUserServiceStrategy;
            case ROLE_MANAGER -> managerUserServiceStrategy;
            default -> throw new IllegalArgumentException("Unsupported role: " + role);
        };
    }
}
