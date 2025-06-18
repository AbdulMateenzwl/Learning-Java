package com.example.demo.shared.assertion;

import com.example.demo.entity.User;
import com.example.demo.enums.UserRole;
import com.example.demo.exceptions.InvalidOperationException;
import com.example.demo.exceptions.UnauthorizedOperationException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserAssert {
    private final UserRepository userRepository;

    public void assertEmailShouldBeUnique(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new InvalidOperationException("User with this email already exists.");
        }
    }

    public User assertUserExists(UUID userId) {
        return userRepository.findByUuid(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void assertUserIsPartOfManager(User user, UUID managerUuid) {
        if (Objects.nonNull(user.getManager()) && !user.getManager().getUuid().equals(managerUuid)) {
            throw new InvalidOperationException("User is not part of the manager's team");
        }
    }

    public void assertUserIsManager(User user, String message){
        if(!user.getRole().equals(UserRole.ROLE_MANAGER)){
            throw new InvalidOperationException(message);
        }
    }

    public void assertUserIsNotAdmin(User user, String message){
        if(user.getRole().equals(UserRole.ROLE_ADMIN)){
            throw new InvalidOperationException(message);
        }
    }

    public void assertUserIsNotRestricted(User user){
        if(user.isRestricted()){
            throw new InvalidOperationException("User is already restricted");
        }
    }

    public void assertUserHasUserRole(User user, String message){
        if (!user.getRole().equals(UserRole.ROLE_USER)) {
            throw new UnauthorizedOperationException(message);
        }
    }
}
