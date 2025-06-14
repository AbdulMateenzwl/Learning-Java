package com.example.demo.service.strategy.gettasks;

import com.example.demo.config.UserContext;
import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.Task;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManagerGetTasksServiceStrategy implements GetTasksServiceStrategy {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserContext userContext;

    @Override
    public Page<TaskDTO> getTasks(Pageable pageable) {
        Page<Task> tasks = taskRepository.findByCreatedByUuid(userContext.getUserId(), pageable);
        return tasks.map(taskMapper::toDTO);
    }
}
