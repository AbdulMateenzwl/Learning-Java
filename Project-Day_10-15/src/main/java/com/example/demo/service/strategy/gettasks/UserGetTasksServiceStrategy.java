package com.example.demo.service.strategy.gettasks;

import com.example.demo.config.UserContext;
import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.Task;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserGetTasksServiceStrategy implements GetTasksServiceStrategy {

    private final TaskRepository taskRepository;
    private final UserContext userContext;
    private final TaskMapper taskMapper;

    @Override
    public Page<TaskDTO> getTasks(Pageable pageable) {
        UUID assignedToUuid = userContext.getUserId();
        Page<Task> tasks = taskRepository.findByAssignedToUuid(assignedToUuid, pageable);
        return tasks.map(taskMapper::toDTO);
    }
}
