package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.ENUM.TaskStatus;
import com.example.demo.Entity.Task;
import com.example.demo.Entity.User;
import com.example.demo.Mapper.TaskMapper;
import com.example.demo.dto.TaskDTO;
import com.example.demo.exceptions.InvalidOperationException;
import com.example.demo.exceptions.TaskAlreadyAssignedException;
import com.example.demo.exceptions.TaskNotFoundException;
import com.example.demo.exceptions.UnauthorizedOperationException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;


    public TaskDTO createTask(TaskDTO taskDTO, UUID userId) {
        taskDTO.setCreatedBy(userId);
        Optional<User> user = userRepository.findByUuid(userId);
        Task task = taskMapper.toEntity(taskDTO, user);
        taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    public Optional<TaskDTO> findByUuid(UUID uuid) {
        return Optional.ofNullable(taskMapper.toDTO(taskRepository.findByUuid(uuid).get()));
    }    public TaskDTO assignTask(UUID taskId, UUID userID, UUID managerId) {
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
    }    public TaskDTO updateTask(TaskDTO taskDTO, UUID taskId, UUID managerId) {
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

    public Page<TaskDTO> getManagersAllTasks(UUID creatorUuid, Pageable pageable) {
        Page<Task> tasks = taskRepository.findByCreatedByUuid(creatorUuid, pageable);
        return tasks.map(taskMapper::toDTO);
    }

    public Page<TaskDTO> getAllTasks(Pageable pageable) {
        Page<Task> tasks = (taskRepository.findAll(pageable));
        return tasks.map(taskMapper::toDTO);
    }

    public Page<TaskDTO> getAllTasksByAssignedUser(UUID assignedToUuid, Pageable pageable) {
        Page<Task> tasks =  taskRepository.findByAssignedToUuid(assignedToUuid, pageable);
        return tasks.map(taskMapper::toDTO);
    }

    public Optional<TaskDTO> getTaskById(UUID taskUuid) {
        return Optional.ofNullable(taskMapper.toDTO(taskRepository.findByUuid(taskUuid).get()));
    }

    public TaskDTO getTaskOfUser(UUID userUuid, UUID taskUuid) {
        Task task = this.getTaskOfUserEntity(userUuid, taskUuid);
        return taskMapper.toDTO(task);
    }    private Task getTaskOfUserEntity(UUID userUuid, UUID taskUuid) {
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

    public TaskDTO updateTaskOfUser(UUID userUuid, UUID taskUuid, TaskStatus status) {
        Task task = this.getTaskOfUserEntity(userUuid, taskUuid);
        if (task.getStatus() == status) {
            throw new InvalidOperationException("Task is already in the requested status");
        }
        task.setStatus(status);
        taskRepository.save(task);
        return taskMapper.toDTO(task);
    }
}
