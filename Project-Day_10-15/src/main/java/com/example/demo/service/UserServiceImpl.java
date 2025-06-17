package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import com.example.demo.service.strategy.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.enums.UserRole;
import com.example.demo.config.UserContext;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.exceptions.InvalidOperationException;
import com.example.demo.exceptions.UnauthorizedOperationException;
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

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new InvalidOperationException("User with this email already exists");
        }
        User user = userMapper.toEntity(userDTO);
        if (user.getRole() == UserRole.ROLE_USER) {
            User manager = userRepository.findByUuid(userDTO.getManagerUuid()).orElseThrow(()-> new UserNotFoundException("Manager not found"));
            if (manager.getRole().equals(UserRole.ROLE_MANAGER)) {
                user.setManager(manager);
            }
        }
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    private Optional<User> findByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid);
    }

    public Optional<UserDTO> findUserByUuid(UUID uuid) {
        Optional<User> user = findByUuid(uuid);
        if (user.isPresent() && user.get().getRole() == UserRole.ROLE_USER) {
            return Optional.of(userMapper.toDTO(user.get()));
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public UserDTO restrictUser(UUID uuid) {
        Optional<User> userOptional = findByUuid(uuid);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User user = userOptional.get();
        if (user.getRole() == UserRole.ROLE_ADMIN) {
            throw new UnauthorizedOperationException("Admin cannot be restricted");
        }

        if (user.isRestricted()) {
            throw new InvalidOperationException("User is already restricted");
        }

        user.setRestricted(true);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public boolean isUserPartOfManager(UUID userUuid, UUID managerUuid) {
        Optional<UserDTO> user = findUserByUuid(userUuid);
        if (user.isPresent() && (user.get().getManagerUuid()) != null) {
            return user.get().getManagerUuid().equals(managerUuid);
        }
        return false;
    }

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        UserService strategy = userServiceFactory.get(userContext.getRole());
        return strategy.getUsers(pageable);
    }

    public UserDTO getCurrentUser() {
        UUID userId = userContext.getUserId();
        return findUserByUuid(userId)
                .orElseThrow(() -> new UserNotFoundException("Current user not found"));
    }
}
