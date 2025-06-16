//package name 
//com.example.demo.service.strategy.users
package com.example.demo.service.strategy.getuser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.UserDTO;

//no verb in class name
//its name should be UserService (no need of strategy word)
public interface GetUserServiceStrategy {
    Page<UserDTO> getUsers(Pageable pageable);
}
