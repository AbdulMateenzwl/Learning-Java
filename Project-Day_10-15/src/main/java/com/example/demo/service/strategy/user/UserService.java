package com.example.demo.service.strategy.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.UserDTO;

public interface UserService {
    Page<UserDTO> getUsers(Pageable pageable);
}
