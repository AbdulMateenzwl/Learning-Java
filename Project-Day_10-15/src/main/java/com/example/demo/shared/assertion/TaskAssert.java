package com.example.demo.shared.assertion;

import com.example.demo.entity.Task;
import com.example.demo.enums.TaskStatus;
import com.example.demo.exceptions.TaskAlreadyAssignedException;
import com.example.demo.exceptions.UnauthorizedOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskAssert {

//    TaskRepository taskRepository;

    public void assertTaskNotAssigned(Task task) {
        if (Objects.nonNull(task.getAssignedTo())) {
            throw new TaskAlreadyAssignedException("Task is already assigned");
        }
    }

    public void assertTaskIsCreatedByUser(Task task, UUID userId) {
        if (!task.getCreatedBy().getUuid().equals(userId)) {
            throw new UnauthorizedOperationException("Only the creator can perform action on the task");
        }
    }

    public void assertTaskStatusIsNotSame(Task task, TaskStatus status){
        if(task.getStatus().equals(status)){
            throw new UnauthorizedOperationException("Task is already in the requested status");
        }
    }

    public void assertTaskAssignedToUser(Task task, UUID userId){
        if(Objects.isNull(task.getAssignedTo()) || !task.getAssignedTo().getUuid().equals(userId)){
            throw new UnauthorizedOperationException("Task is not assigned to the user");
        }
    }

}
