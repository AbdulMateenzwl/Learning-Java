package com.example.demo.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.enums.TaskStatus;
import com.example.demo.config.PaginationConfig;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.TaskDTO;
import com.example.demo.service.TaskService;
import com.example.demo.utils.ApiResponseUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final PaginationConfig paginationConfig;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<TaskDTO>> createTask(@RequestBody TaskDTO taskDTO) {
        return ApiResponseUtil.created(taskService.createTask(taskDTO), "Task created successfully");
    }

    @PatchMapping("/{taskUuid}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<TaskDTO>> assignTask(@PathVariable UUID taskUuid, @RequestParam UUID userUuid) {
        return ApiResponseUtil.success(taskService.assignTask(taskUuid, userUuid), "Task assigned successfully");
    }

    @PutMapping("/{taskUuid}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<TaskDTO>> updateUserTask(@RequestBody(required = false) TaskDTO taskDTO,
                                                               @RequestParam(required = false) TaskStatus status,
                                                               @PathVariable UUID taskUuid) {
        return ApiResponseUtil.success(taskService.updateTask(taskUuid, taskDTO, status), "Task updated successfully");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Page<TaskDTO>>> getUsersAllTasks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "createdAt,asc") String[] sort) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(sort[1]), sort[0]);
        int validatedSize = Math.min(size, paginationConfig.getMaxPageSize());
        Pageable pageable = PageRequest.of(page, validatedSize, sortOrder);

        return ApiResponseUtil.success(taskService.getAllTasks(pageable), "Tasks retrieved successfully");
    }

    @GetMapping("/{taskUuid}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<TaskDTO>> getTaskById(@PathVariable UUID taskUuid) {
        return ApiResponseUtil.success(taskService.getTaskOfUser(taskUuid), "Task retrieved successfully");
    }

}
