package com.example.demo.dto;

import com.example.demo.ENUM.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskDTO {

    private UUID uuid;
    private String title;
    private String description;
    private TaskStatus status;

    private UUID createdBy;
    private UUID assignedTo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
