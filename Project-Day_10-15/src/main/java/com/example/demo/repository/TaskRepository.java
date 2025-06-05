package com.example.demo.repository;

import com.example.demo.Entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByUuid(UUID uuid);

    Page<Task> findByCreatedByUuid(UUID createdByUuid, Pageable pageable);

    Page<Task> findByAssignedToUuid(UUID assignedToUuid, Pageable pageable);

//    Page<Task> findAll(Pageable pageable);
}
