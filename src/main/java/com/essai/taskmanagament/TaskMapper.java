package com.essai.taskmanagament;

import com.essai.taskmanagament.dto.TaskDTO;
import com.essai.taskmanagament.entity.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO toTaskDTO(Task task);
    List<TaskDTO> toDTOList(List<Task> tasks);
    Task toTask(TaskDTO taskDTO);
}
