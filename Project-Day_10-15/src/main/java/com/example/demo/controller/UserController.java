package com.example.demo.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import com.example.demo.config.UserContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.ENUM.UserRole;
import com.example.demo.config.PaginationConfig;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import com.example.demo.utils.ApiResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final UserContext userContext;
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
        log.info("Received Request: Retrieving all user roles");
        return ApiResponseUtil.success(Stream.of(UserRole.values()).map(Enum::name).collect(java.util.stream.Collectors.toList()), "Roles retrieved successfully");
    }

    @GetMapping("/info")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser() {
        return ApiResponseUtil.success(userService.getCurrentUser(), "Current user retrieved successfully");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "createdAt,asc") String[] sort) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(sort[1]), sort[0]);

        int validatedSize = Math.min(size, paginationConfig.getMaxPageSize());

        Pageable pageable = PageRequest.of(page, validatedSize, sortOrder);

        return ApiResponseUtil.success(userService.getAllUsers(pageable), "Assigned users retrieved successfully");
    }

}
