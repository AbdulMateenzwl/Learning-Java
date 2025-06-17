package com.example.demo.service.factory;

import com.example.demo.enums.UserRole;
import com.example.demo.service.strategy.tasks.AdminTaskService;
import com.example.demo.service.strategy.tasks.TaskService;
import com.example.demo.service.strategy.tasks.ManagerTaskService;
import com.example.demo.service.strategy.tasks.UserTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskServiceFactory {
    private final UserTaskService userGetTasksServiceStrategy;
    private final ManagerTaskService managerGetTasksServiceStrategy;
    private final AdminTaskService adminGetTasksServiceStrategy;

    public TaskService get(UserRole role) {
        return switch (role) {
            case ROLE_ADMIN -> adminGetTasksServiceStrategy;
            case ROLE_MANAGER -> managerGetTasksServiceStrategy;
            case ROLE_USER -> userGetTasksServiceStrategy;
            default -> throw new IllegalArgumentException("Unsupported role: " + role);
        };
    }
}
