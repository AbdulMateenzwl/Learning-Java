package com.example.demo.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ENUM.UserRole;
import com.example.demo.config.PaginationConfig;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import com.example.demo.utils.ApiResponseUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final PaginationConfig paginationConfig;


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminEndpoint() {
        return "Admin only endpoint";
    }

    @GetMapping("/manager")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String managerEndpoint() {
        return "Manager or Admin endpoint";
    }

    @Operation(
            summary = "Create a new user (Admin only)",
            description = "Allows an administrator to create a new user account with specified details. Requires 'ROLE_ADMIN' authority.",
            tags = {"User Management"}, // You can add specific tags here
            security = @SecurityRequirement(name = "Bearer Authentication"), // References your security scheme defined in OpenAPI config
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "User created successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiResponse.class, subTypes = {UserDTO.class}))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Bad Request: Invalid user data provided",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiResponse.class)) // Assuming ApiResponse handles errors
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized: Authentication required",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiResponse.class))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "Forbidden: User does not have 'ROLE_ADMIN' authority",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiResponse.class))
                    )
            }
    )
    @PostMapping("/create/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<UserDTO>> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User details for the new account",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDTO.class))
            )
            @RequestBody UserDTO userDTO
    ) {
        log.info("Received Request: Creating user with details: {}", userDTO);
        UserDTO createdUser = userService.createUser(userDTO);
        return ApiResponseUtil.created(createdUser, "User created successfully");
    }

    @PostMapping("/admin/create/manager")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<UserDTO>> createManager(@RequestBody UserDTO userDTO) {
        log.info("Received Request: Creating manager with details: {}", userDTO);
        UserDTO createdUser = userService.createManager(userDTO);
        return ApiResponseUtil.created(createdUser, "Manager created successfully");

    }

    @PostMapping("/admin/create/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<UserDTO>> createAdmin(@RequestBody UserDTO userDTO) {
        log.info("Received Request: Creating admin with details: {}", userDTO);
        UserDTO createdUser = userService.createAdmin(userDTO);
        return ApiResponseUtil.created(createdUser, "Admin created successfully");
    }

    @PostMapping("/admin/restrict_user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<UserDTO>> restrictUser(@RequestParam UUID uuid) {
        log.info("Received Request: Restricting user with UUID: {}", uuid);
        UserDTO userDto = userService.restrictUser(uuid);
        return ApiResponseUtil.success(userDto, "User restricted successfully");
    }

    @GetMapping("/admin/user/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getAllUsersAndManagers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "createdAt,asc") String[] sort) {
        log.info("Received Request: Getting all users and managers with page: {}, size: {}, sort: {}", page, size, (Object) sort);
        Sort sortOrder = Sort.by(Sort.Direction.fromString(sort[1]), sort[0]);

        int validatedSize = Math.min(size, paginationConfig.getMaxPageSize());

        Pageable pageable = PageRequest.of(page, validatedSize, sortOrder);
        return ApiResponseUtil.success(userService.findAllUsersAndManagers(pageable), "Users and Managers retrieved successfully");
    }

    @GetMapping("/admin/user/roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<String>>> getAllRoles() {
        log.info("Received Request: Retrieving all user roles");
        return ApiResponseUtil.success(Stream.of(UserRole.values()).map(Enum::name).collect(java.util.stream.Collectors.toList()), "Roles retrieved successfully");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser(@RequestHeader("Authorization") String token) {
        log.info("Received Request: Retrieving current user from token: {}", token);
        String uuid = jwtService.extractUserUUID(token.substring(7));
        return ApiResponseUtil.success(userService.findUserByUuid(UUID.fromString(uuid)).get(), "Current user retrieved successfully");
    }


    @GetMapping("/manager/assigned_user")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getAssignedUsers(@RequestHeader("Authorization") String token, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "createdAt,asc") String[] sort) {
        log.info("Received Request: Retrieving assigned users with page: {}, size: {}, sort: {}", page, size, (Object) sort);
        String uuid = jwtService.extractUserUUID(token.substring(7));
        Sort sortOrder = Sort.by(Sort.Direction.fromString(sort[1]), sort[0]);

        int validatedSize = Math.min(size, paginationConfig.getMaxPageSize());

        Pageable pageable = PageRequest.of(page, validatedSize, sortOrder);

        return ApiResponseUtil.success(userService.findAllAssignedUsers(UUID.fromString(uuid), pageable), "Assigned users retrieved successfully");
    }

}
