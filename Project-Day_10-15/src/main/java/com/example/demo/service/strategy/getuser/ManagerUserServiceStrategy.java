package com.example.demo.service.strategy.getuser;

import java.util.UUID;

import com.example.demo.Entity.User;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.config.UserContext;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ManagerUserServiceStrategy implements GetUserServiceStrategy {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserContext userContext;

    @Override
    public Page<UserDTO> getUsers(Pageable pageable) {
        UUID managerUuid = userContext.getUserId();
        Page<User> users = userRepository.findByManagerUuid(managerUuid, pageable);
        return users.map(userMapper::toDTO);
    }
}
