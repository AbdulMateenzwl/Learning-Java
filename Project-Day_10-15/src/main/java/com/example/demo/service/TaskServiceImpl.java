package com.example.demo.service;

import java.util.Objects;
import java.util.UUID;

import com.example.demo.config.PaginationConfig;
import com.example.demo.config.UserContext;
import com.example.demo.enums.UserRole;
import com.example.demo.service.factory.TaskServiceFactory;
import com.example.demo.service.strategy.tasks.TaskService;
import com.example.demo.shared.assertion.TaskAssert;
import com.example.demo.shared.assertion.UserAssert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.enums.TaskStatus;
import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exceptions.TaskNotFoundException;
import com.example.demo.exceptions.UnauthorizedOperationException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl {
    private final UserContext userContext;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskServiceFactory taskServiceFactory;
    private final TaskAssert taskAssert;
    private final UserAssert userAssert;
    private final PaginationConfig paginationConfig;

    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        UUID userId = userContext.getUserId();

        taskDTO.setCreatedBy(userId);
        User user = userRepository.findByUuid(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        Task task = taskMapper.toEntity(taskDTO, user);
        taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Transactional
    public TaskDTO assignTask(UUID taskId, UUID userId) {
        UUID managerId = userContext.getUserId();

        Task task = taskRepository.findByUuid(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskAssert.assertTaskNotAssigned(task);
        taskAssert.assertTaskIsCreatedByUser(task, managerId);
        User user = userAssert.assertUserExists(userId);
        userAssert.assertUserIsPartOfManager(user, managerId);

        task.setAssignedTo(user);
        taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Transactional
    protected TaskDTO updateTask(TaskDTO taskDTO, UUID taskId) {
        UUID managerId = userContext.getUserId();

        Task task = taskRepository.findByUuid(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskAssert.assertTaskIsCreatedByUser(task, managerId);

        task = taskMapper.updateTask(task, taskDTO);

        taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Transactional
    protected TaskDTO updateTask(UUID taskUuid, TaskStatus status) {
        UUID userUuid = userContext.getUserId();
        Task task = taskRepository.findByUuid(taskUuid).orElseThrow(() -> new TaskNotFoundException("Task not found"));

        taskAssert.assertTaskAssignedToUser(task, userUuid);
        taskAssert.assertTaskStatusIsNotSame(task, status);

        task.setStatus(status);
        taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Transactional
    public TaskDTO updateTask(UUID taskUuid, TaskDTO taskDTO, TaskStatus status) {

        UserRole role = userContext.getRole();

        switch (role) {
            case ROLE_MANAGER -> {
                if (Objects.nonNull(taskDTO)) {
                    return updateTask(taskDTO, taskUuid);
                }
            }
            case ROLE_USER -> {
                if (Objects.nonNull(status)) {
                    return updateTask(taskUuid, status);
                }
            }
        }
        throw new UnauthorizedOperationException("Only Manager and User can update the Task");
    }

    public Page<TaskDTO> getAllTasks(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(),
                Math.min(pageable.getPageSize(), paginationConfig.getMaxPageSize()),
                pageable.getSort());

        TaskService strategy = taskServiceFactory.get(userContext.getRole());
        return strategy.getTasks(pageable);
    }

    public TaskDTO getTaskOfUser(UUID taskUuid) {
        UUID userUuid = userContext.getUserId();

        Task task = taskRepository.findByUuid(taskUuid).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskAssert.assertTaskAssignedToUser(task, userUuid);

        return taskMapper.toDTO(task);
    }
}
