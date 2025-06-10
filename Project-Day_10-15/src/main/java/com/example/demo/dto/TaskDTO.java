package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.ENUM.TaskStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskDTO {

    private UUID uuid;
    @NotNull
    @Size(max = 100)
    private String title;

    @Size(max = 500)
    private String description;
    private TaskStatus status;

    private UUID createdBy;
    private UUID assignedTo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
