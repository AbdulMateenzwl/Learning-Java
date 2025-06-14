package com.example.demo.controller;

import com.example.demo.config.PaginationConfig;
import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.ApiResponseUserDTO;
import com.example.demo.dto.ApiResponseUserPageDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.enums.UserRole;
import com.example.demo.service.UserService;
import com.example.demo.utils.ApiResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;
    private final PaginationConfig paginationConfig;

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details. Only accessible by administrators.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseUserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponseDTO<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ApiResponseUtil.created(createdUser, "User created successfully");
    }

    @Operation(
            summary = "Restrict a user",
            description = "Restricts a user or manager by UUID. Only accessible by administrators.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User restricted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseUserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request | User Already Restricted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @PatchMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponseDTO<UserDTO>> restrictUser(@PathVariable UUID uuid) {
        UserDTO userDto = userService.restrictUser(uuid);
        return ApiResponseUtil.success(userDto, "User restricted successfully");
    }

    @Operation(
            summary = "Get List of User Roles",
            description = "Get List of User Roles in System - Only Admin can Access")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles Received", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @GetMapping("/roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponseDTO<List<String>>> getAllRoles() {
        return ApiResponseUtil.success(
                Stream.of(UserRole.values()).map(Enum::name).collect(java.util.stream.Collectors.toList()),
                "Roles retrieved successfully");
    }

    @Operation(
            summary = "Get User info",
            description = "Get user Details of the current user. Accessible by any authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles Received", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @GetMapping("/info")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponseDTO<UserDTO>> getCurrentUser() {
        return ApiResponseUtil.success(userService.getCurrentUser(), "Current user retrieved successfully");
    }

    @Operation(
            summary = "Get paginated list of users",
            description = """
                    Retrieves a paginated list of users:
                    - For admins: Returns all users
                    - For managers: Returns only assigned users
                    Default pagination: page 0, size 5, sorted by createdAt ascending
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseUserPageDTO.class))),
            @ApiResponse(responseCode = "403", description = "Not authorized to view users", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ApiResponseDTO<Page<UserDTO>>> getUsers(
            @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(),
                Math.min(pageable.getPageSize(), paginationConfig.getMaxPageSize()),
                pageable.getSort());

        return ApiResponseUtil.success(userService.getAllUsers(pageable), "Assigned users retrieved successfully");
    }

}
