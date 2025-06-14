package com.example.demo.controller;

import java.util.UUID;

import com.example.demo.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.PaginationConfig;
import com.example.demo.enums.TaskStatus;
import com.example.demo.service.TaskService;
import com.example.demo.utils.ApiResponseUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TaskController {
    private final TaskService taskService;
    private final PaginationConfig paginationConfig;

    @Operation(summary = "Create a new task", description = "Creates a new task with the provided details. Only accessible by manager.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseTaskDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponseDTO<TaskDTO>> createTask(@RequestBody TaskDTO taskDTO) {
        return ApiResponseUtil.created(taskService.createTask(taskDTO), "Task created successfully");
    }

    @Operation(summary = "Assign task", description = "Assigned a Task to User by Uuid - Manager only")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assigned Task to User successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseTaskDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class)))
    })

    @PatchMapping("/{taskUuid}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponseDTO<TaskDTO>> assignTask(@PathVariable UUID taskUuid, @RequestParam UUID userUuid) {
        return ApiResponseUtil.success(taskService.assignTask(taskUuid, userUuid), "Task assigned successfully");
    }

    @Operation(summary = "Update Task", description = """
            Update a Task by Uuid - Manager
            Update Task status - User
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Task", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseTaskDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @PutMapping("/{taskUuid}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER')")
    public ResponseEntity<ApiResponseDTO<TaskDTO>> updateUserTask(@RequestBody(required = false) TaskDTO taskDTO,
                                                                  @RequestParam(required = false) TaskStatus status,
                                                                  @PathVariable UUID taskUuid) {
        return ApiResponseUtil.success(taskService.updateTask(taskUuid, taskDTO, status), "Task updated successfully");
    }

    @Operation(summary = "Get Tasks", description = """
            User - Get Page of Assigned Tasks
            Manager - Get Page of All Tasks which are created by him
            Admin - Get Page of All Tasks in System
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task Retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseTaskPageDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<ApiResponseDTO<Page<TaskDTO>>> getUsersAllTasks(
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(),
                Math.min(pageable.getPageSize(), paginationConfig.getMaxPageSize()),
                pageable.getSort());

        return ApiResponseUtil.success(taskService.getAllTasks(pageable), "Tasks retrieved successfully");
    }

    @Operation(summary = "Get a User Task", description = "Get a single Task assigned to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Task", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseTaskDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @GetMapping("/{taskUuid}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponseDTO<TaskDTO>> getTaskById(@PathVariable UUID taskUuid) {
        return ApiResponseUtil.success(taskService.getTaskOfUser(taskUuid), "Task retrieved successfully");
    }

}
