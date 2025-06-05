package com.example.demo.dto;

import java.util.UUID;

import com.example.demo.ENUM.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private UUID uuid;
    private String email;
    private String password;
    private String role;
    private boolean restricted;
    private UUID managerUuid;
}
