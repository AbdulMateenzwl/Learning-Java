package com.example.demo.service.strategy.gettasks;

import com.example.demo.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetTasksServiceStrategy {
    Page<TaskDTO> getTasks(Pageable pageable);
}
