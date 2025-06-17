package com.example.demo.service.strategy.tasks;

import com.example.demo.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Page<TaskDTO> getTasks(Pageable pageable);
}
