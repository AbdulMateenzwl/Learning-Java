package com.example.demo.service.factory;

import com.example.demo.enums.UserRole;
import com.example.demo.service.strategy.gettasks.AdminGetTasksServiceStrategy;
import com.example.demo.service.strategy.gettasks.GetTasksServiceStrategy;
import com.example.demo.service.strategy.gettasks.ManagerGetTasksServiceStrategy;
import com.example.demo.service.strategy.gettasks.UserGetTasksServiceStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetTasksServiceStrategyFactory {
    private final UserGetTasksServiceStrategy userGetTasksServiceStrategy;
    private final ManagerGetTasksServiceStrategy managerGetTasksServiceStrategy;
    private final AdminGetTasksServiceStrategy adminGetTasksServiceStrategy;

    public GetTasksServiceStrategy createStrategy(UserRole role) {
        return switch (role) {
            case ROLE_ADMIN -> adminGetTasksServiceStrategy;
            case ROLE_MANAGER -> managerGetTasksServiceStrategy;
            case ROLE_USER -> userGetTasksServiceStrategy;
            default -> throw new IllegalArgumentException("Unsupported role: " + role);
        };
    }
}
