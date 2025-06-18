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
    private final UserTaskService userTaskService;
    private final ManagerTaskService managerTaskService;
    private final AdminTaskService adminTaskService;

    public TaskService get(UserRole role) {
        return switch (role) {
            case ROLE_ADMIN -> adminTaskService;
            case ROLE_MANAGER -> managerTaskService;
            case ROLE_USER -> userTaskService;
        };
    }
}
