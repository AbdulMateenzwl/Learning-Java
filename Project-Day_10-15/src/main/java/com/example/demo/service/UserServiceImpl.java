package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import com.example.demo.config.PaginationConfig;
import com.example.demo.service.strategy.user.UserService;
import com.example.demo.shared.assertion.UserAssert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.enums.UserRole;
import com.example.demo.config.UserContext;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.factory.UserServiceFactory;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserServiceFactory userServiceFactory;
    private final UserContext userContext;
    private final UserAssert userAssert;
    private final PaginationConfig paginationConfig;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        userAssert.assertEmailShouldBeUnique(userDTO.getEmail());

        User user = userMapper.toEntity(userDTO);

        assignManagerIfApplicable(user, userDTO.getManagerUuid());

        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    private void assignManagerIfApplicable(User user, UUID managerId) {
        if (user.getRole() == UserRole.ROLE_USER) {
            User manager = userRepository.findByUuid(managerId).orElseThrow(() -> new UserNotFoundException("Manager not found"));
            userAssert.assertUserIsManager(manager, "User cannot be assigned to a user who is not a Manager");
            user.setManager(manager);
        }
    }

    @Transactional
    public UserDTO restrictUser(UUID uuid) {
        User user = userRepository.findByUuid(uuid).orElseThrow(() -> new UserNotFoundException("User not found"));

        userAssert.assertUserIsNotAdmin(user, "Admin cannot be restricted");
        userAssert.assertUserIsNotRestricted(user);

        user.setRestricted(true);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(),
                Math.min(pageable.getPageSize(), paginationConfig.getMaxPageSize()),
                pageable.getSort());

        UserService strategy = userServiceFactory.get(userContext.getRole());
        return strategy.getUsers(pageable);
    }

    public UserDTO getCurrentUser() {
        UUID userId = userContext.getUserId();
        User user = userRepository.findByUuid(userId).orElseThrow(() -> new UserNotFoundException("User not Found"));
        userAssert.assertUserHasUserRole(user, "Not Authorized to access the User Details");

        return userMapper.toDTO(user);
    }

    public List<String> getRoles() {
        return Stream.of(UserRole.values()).map(Enum::name).collect(java.util.stream.Collectors.toList());
    }
}
