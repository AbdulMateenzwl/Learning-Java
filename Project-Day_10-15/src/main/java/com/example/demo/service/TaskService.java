package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import com.example.demo.config.UserContext;
import com.example.demo.enums.UserRole;
import com.example.demo.service.factory.GetTasksServiceStrategyFactory;
import com.example.demo.service.strategy.gettasks.GetTasksServiceStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.enums.TaskStatus;
import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exceptions.InvalidOperationException;
import com.example.demo.exceptions.TaskAlreadyAssignedException;
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
public class TaskService {
    private final UserService userService;
    private final UserContext userContext;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final GetTasksServiceStrategyFactory getTasksServiceStrategyFactory;

    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        UUID userId = userContext.getUserId();
        taskDTO.setCreatedBy(userId);
        Optional<User> user = userRepository.findByUuid(userId);
        Task task = taskMapper.toEntity(taskDTO, user);
        taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Transactional
    public TaskDTO assignTask(UUID taskId, UUID userID) {
        UUID managerId = userContext.getUserId();
        Optional<Task> optionalTask = taskRepository.findByUuid(taskId);
        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }
        Task task = optionalTask.get();
        if (task.getAssignedTo() != null) {
            throw new TaskAlreadyAssignedException("Task is already assigned");
        }
        if (!task.getCreatedBy().getUuid().equals(managerId)) {
            throw new UnauthorizedOperationException("Only the creator can assign the task");
        }
        Optional<User> user = userRepository.getReferenceByUuid(userID);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        if (!userService.isUserPartOfManager(user.get().getUuid(), managerId)) {
            throw new UnauthorizedOperationException("User is not part of the manager's team");
        }

        task.setAssignedTo(user.get());
        taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Transactional
    protected TaskDTO updateTask(TaskDTO taskDTO, UUID taskId) {
        UUID managerId = userContext.getUserId();
        Optional<Task> optionalTask = taskRepository.findByUuid(taskId);
        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }
        Task task = optionalTask.get();
        if (!task.getCreatedBy().getUuid().equals(managerId)) {
            throw new UnauthorizedOperationException("Only the creator can update the task");
        }

        task = taskMapper.updateTask(task, taskDTO);

        taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Transactional
    protected TaskDTO updateTask(UUID taskUuid, TaskStatus status) {
        UUID userUuid = userContext.getUserId();
        Task task = this.getTaskOfUserEntity(userUuid, taskUuid);
        if (task.getStatus() == status) {
            throw new InvalidOperationException("Task is already in the requested status");
        }
        task.setStatus(status);
        taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Transactional
    public TaskDTO updateTask(UUID taskUuid, TaskDTO taskDTO, TaskStatus status) {

        UserRole role = userContext.getRole();

        switch (role) {
            case ROLE_MANAGER -> {
                if (taskDTO != null) {
                    return updateTask(taskDTO, taskUuid);
                }
            }
            case ROLE_USER -> {
                if (status != null) {
                    return updateTask(taskUuid, status);
                }
            }
        }
        throw new UnauthorizedOperationException("Only Manager and User can update the Task");
    }

    public Page<TaskDTO> getAllTasks(Pageable pageable) {
        GetTasksServiceStrategy strategy = getTasksServiceStrategyFactory.createStrategy(userContext.getRole());
        return strategy.getTasks(pageable);
    }

    public TaskDTO getTaskOfUser(UUID taskUuid) {
        UUID userUuid = userContext.getUserId();
        Task task = this.getTaskOfUserEntity(userUuid, taskUuid);
        return taskMapper.toDTO(task);
    }

    private Task getTaskOfUserEntity(UUID userUuid, UUID taskUuid) {
        Optional<Task> optionalTask = taskRepository.findByUuid(taskUuid);
        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }
        Task task = optionalTask.get();
        if (!task.getAssignedTo().getUuid().equals(userUuid)) {
            throw new UnauthorizedOperationException("Task is not assigned to the user");
        }
        return task;
    }
}
