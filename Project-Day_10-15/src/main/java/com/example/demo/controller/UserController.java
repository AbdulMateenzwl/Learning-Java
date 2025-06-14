package com.example.demo.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.enums.UserRole;
import com.example.demo.config.PaginationConfig;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import com.example.demo.utils.ApiResponseUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PaginationConfig paginationConfig;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ApiResponseUtil.created(createdUser, "Manager created successfully");
    }


    @PatchMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<UserDTO>> restrictUser(@PathVariable UUID uuid) {
        UserDTO userDto = userService.restrictUser(uuid);
        return ApiResponseUtil.success(userDto, "User restricted successfully");
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<String>>> getAllRoles() {
        return ApiResponseUtil.success(Stream.of(UserRole.values()).map(Enum::name).collect(java.util.stream.Collectors.toList()), "Roles retrieved successfully");
    }

    @GetMapping("/info")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser() {
        return ApiResponseUtil.success(userService.getCurrentUser(), "Current user retrieved successfully");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getUsers(@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(),
                Math.min(pageable.getPageSize(), paginationConfig.getMaxPageSize()),
                pageable.getSort());

        return ApiResponseUtil.success(userService.getAllUsers(pageable), "Assigned users retrieved successfully");
    }

}
