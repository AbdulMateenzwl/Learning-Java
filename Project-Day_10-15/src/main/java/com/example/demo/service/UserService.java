package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.config.UserContext;
import com.example.demo.service.factory.GetUserServiceStrategyFactory;
import com.example.demo.service.strategy.getuser.GetUserServiceStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.ENUM.UserRole;
import com.example.demo.Entity.User;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.dto.UserDTO;
import com.example.demo.exceptions.InvalidOperationException;
import com.example.demo.exceptions.UnauthorizedOperationException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final GetUserServiceStrategyFactory userServiceStrategyFactory;
    private final UserContext userContext;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        Optional<User> createdBy = userRepository.findByUuid(userDTO.getManagerUuid());
        User user = userMapper.toEntity(userDTO, createdBy);
        user.setRole(UserRole.ROLE_USER);

        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO createManager(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setRole(UserRole.ROLE_MANAGER);

        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO createAdmin(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setRole(UserRole.ROLE_ADMIN);

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


    public Optional<UserDTO> findManagerByUuid(UUID uuid) {
        Optional<User> user = findByUuid(uuid);
        if (user.isPresent() && user.get().getRole() == UserRole.ROLE_MANAGER) {
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

    public Page<UserDTO> findAllUsersAndManagers(Pageable pageable) {
        Page<User> users = userRepository.findByRoleIn(List.of(UserRole.ROLE_USER, UserRole.ROLE_MANAGER), pageable);
        return users.map(userMapper::toDTO);
    }

    public Page<UserDTO> findAllAssignedUsers(UUID managerUuid, Pageable pageable) {
        Page<User> users = userRepository.findByManagerUuid(managerUuid, pageable);
        return users.map(userMapper::toDTO);
    }

    public boolean isUserPartOfManager(UUID userUuid, UUID managerUuid) {
        Optional<UserDTO> user = findUserByUuid(userUuid);
        if (user.isPresent() && (user.get().getManagerUuid()) != null) {
            return user.get().getManagerUuid().equals(managerUuid);
        }
        return false;
    }

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        GetUserServiceStrategy strategy = userServiceStrategyFactory.createStrategy(userContext.getRole());
        return strategy.getUsers(pageable);
    }
}
