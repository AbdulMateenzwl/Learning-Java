package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.ENUM.UserRole;
import com.example.demo.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUuid(UUID uuid);

    Optional<User> findByEmail(String email);    Optional<User> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    Page<User> findByRoleIn(List<UserRole> roles, Pageable pageable);

    List<User> findByRoleIn(List<UserRole> roles);

    Page<User> findByManagerUuid(UUID managerUuid, Pageable pageable);

    Optional<User> getReferenceByUuid(UUID uuid);
}