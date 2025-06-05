package com.example.demo.Mapper;

import java.util.List;
import java.util.Optional;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.demo.Entity.Task;
import com.example.demo.Entity.User;
import com.example.demo.dto.TaskDTO;

@Mapper(componentModel = "spring")
public abstract class TaskMapper {

    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdBy", source = "createdBy.uuid")
    @Mapping(target = "assignedTo", source = "assignedTo.uuid")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    public abstract TaskDTO toDTO(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
    @Mapping(target = "createdBy", ignore = true) 
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Task toEntity(TaskDTO taskDTO);

    public Task toEntity(TaskDTO taskDTO, Optional<User> createdBy) {
        Task task = toEntity(taskDTO);
        task.setCreatedBy(createdBy.orElseThrow(() -> new IllegalArgumentException("Creator user not found")));
        return task;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract Task updateTask(@MappingTarget Task task, TaskDTO taskDTO);

    public abstract List<TaskDTO> toDTOs(List<Task> tasks);
}
