package com.example.demo.service.strategy.getuser;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.demo.enums.UserRole;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminGetUserServiceStrategy implements GetUserServiceStrategy {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserDTO> getUsers(Pageable pageable) {
        Page<User> users = userRepository.findByRoleIn(List.of(UserRole.ROLE_USER, UserRole.ROLE_MANAGER), pageable);
        return users.map(userMapper::toDTO);
    }
}
