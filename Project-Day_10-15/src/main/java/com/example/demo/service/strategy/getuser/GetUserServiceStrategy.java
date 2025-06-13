package com.example.demo.service.strategy.getuser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.UserDTO;

public interface GetUserServiceStrategy {
    Page<UserDTO> getUsers(Pageable pageable);
}
