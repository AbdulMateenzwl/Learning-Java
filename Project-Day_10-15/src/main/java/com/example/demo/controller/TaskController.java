package com.example.demo.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ENUM.TaskStatus;
import com.example.demo.config.PaginationConfig;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.TaskDTO;
import com.example.demo.service.JwtService;
import com.example.demo.service.TaskService;
import com.example.demo.utils.ApiResponseUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/task") //user Plural Nouns
@RequiredArgsConstructor
public class TaskController {
    private final JwtService jwtService;
    private final TaskService taskService;
    private final PaginationConfig paginationConfig;

    //there should be no verb in api path
    //remove create
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<TaskDTO>> createTask(@RequestBody TaskDTO taskDTO, @RequestHeader("Authorization") String token) {
        UUID uuid = UUID.fromString(jwtService.extractUserUUID(token.substring(7)));
        return ApiResponseUtil.created(taskService.createTask(taskDTO, uuid), "Task created successfully");
    }

    //remove assign
    //change PutMapping to PatchMapping as we are just changing the user for that task 
    @PutMapping("/assign")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<TaskDTO>> assignTask(@RequestParam UUID taskUuid, @RequestParam UUID userUuid, @RequestHeader("Authorization") String token) {
        String managerUuid = jwtService.extractUserUUID(token.substring(7));
        return ApiResponseUtil.success(taskService.assignTask(taskUuid, userUuid, UUID.fromString(managerUuid)), "Task assigned successfully");
    }

    //again, no need to add verb 'update'
    //no need to add /manager
    //by reading that endpoint, it seems like you are updating a manager not task
    //to update a task, the endpoint should be PUT /api/tasks/{taskUuid}
    @PutMapping("/manager/update")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<TaskDTO>> updateTask(@RequestBody TaskDTO taskDTO, @RequestParam UUID taskUuid, @RequestHeader("Authorization") String token) {
        String managerUuid = jwtService.extractUserUUID(token.substring(7));
        return ApiResponseUtil.success(taskService.updateTask(taskDTO, taskUuid, UUID.fromString(managerUuid)), "Task updated successfully");
    }

    //the endpoint should be: GET /api/tasks (remove manager/all) 
    @GetMapping("/manager/all")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<Page<TaskDTO>>> getManagersAllTasks(@RequestHeader("Authorization") String token, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "createdAt,asc") String[] sort) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(sort[1]), sort[0]);
        int validatedSize = Math.min(size, paginationConfig.getMaxPageSize());
        Pageable pageable = PageRequest.of(page, validatedSize, sortOrder);

        String managerUuid = jwtService.extractUserUUID(token.substring(7));

        return ApiResponseUtil.success(taskService.getManagersAllTasks(UUID.fromString(managerUuid), pageable), "Tasks retrieved successfully");
    }

    //the endpoint should be: GET /api/tasks (remove admin/all)
    //you may implement a strategy pattern 
    //where TaskService interface can have multiple implementations like
    //AdminTaskService
    //ManagerTaskService
    //UserTaskService
    //they return the tasks as per required info
    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Page<TaskDTO>>> getAllTasksAdmin(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "createdAt,asc") String[] sort) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(sort[1]), sort[0]);
        int validatedSize = Math.min(size, paginationConfig.getMaxPageSize());
        Pageable pageable = PageRequest.of(page, validatedSize, sortOrder);

        return ApiResponseUtil.success(taskService.getAllTasks(pageable), "Retrieved All Tasks successfully");
    }

    //no need to add /all
    //check above ^ 
    //the endpoint should be: GET /api/tasks (remove all)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<Page<TaskDTO>>> getUsersAllTasks(@RequestHeader("Authorization") String token, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "createdAt,asc") String[] sort) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(sort[1]), sort[0]);
        int validatedSize = Math.min(size, paginationConfig.getMaxPageSize());
        Pageable pageable = PageRequest.of(page, validatedSize, sortOrder);

        String userUuid = jwtService.extractUserUUID(token.substring(7));

        return ApiResponseUtil.success(taskService.getAllTasksByAssignedUser(UUID.fromString(userUuid), pageable), "Tasks retrieved successfully");
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<TaskDTO>> getTaskById(@RequestHeader("Authorization") String token, @PathVariable UUID uuid) {
        String userUuid = jwtService.extractUserUUID(token.substring(7));
        return ApiResponseUtil.success(taskService.getTaskOfUser(UUID.fromString(userUuid), uuid), "Task retrieved successfully");
    }

    //remove update
    //if there are multiple requirement as per roles
    //PUT /api/tasks/{taskUuid}
    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<TaskDTO>> updateUserTask(@RequestParam TaskStatus status, @RequestParam UUID taskUuid, @RequestHeader("Authorization") String token) {
        String userUuid = jwtService.extractUserUUID(token.substring(7));
        return ApiResponseUtil.success(taskService.updateTaskOfUser(UUID.fromString(userUuid), taskUuid, status), "Task updated successfully");
    }
}
