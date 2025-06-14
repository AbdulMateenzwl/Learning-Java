package com.example.demo.Mapper;

import java.util.List;
import java.util.Optional;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.Entity.User;
import com.example.demo.dto.UserDTO;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "restricted", source = "restricted")
    @Mapping(target = "managerUuid", source = "manager.uuid")
    @Mapping(target = "password", ignore = true)
    public abstract UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "restricted", source = "restricted")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract User toEntity(UserDTO userDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "restricted", source = "restricted")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract User toEntityWithManager(UserDTO userDTO);

    @AfterMapping
    protected void encodePassword(UserDTO userDTO, @MappingTarget User user) {
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    }

    public User toEntity(UserDTO userDTO, Optional<User> managerOpt) {
        User user = toEntityWithManager(userDTO);
        managerOpt.ifPresent(user::setManager);
        return user;
    }

    public abstract List<UserDTO> toDTOs(List<User> users);
}
