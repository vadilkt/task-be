package com.essai.taskmanagament.service;

import com.essai.taskmanagament.dto.TaskRequestDTO;
import com.essai.taskmanagament.dto.TaskResponseDTO;

import java.util.List;
import java.util.Optional;

public interface TaskService<T, ID> {
    TaskResponseDTO addTask(TaskRequestDTO taskRequestDTO);
    List<TaskResponseDTO> getAllTasks();
    Optional<TaskResponseDTO> getTaskById(ID id);
    Optional<TaskResponseDTO> getTaskByTitle(String title);
    TaskResponseDTO updateTask(ID id, TaskRequestDTO taskRequestDTO);
    void deleteTaskById(ID id);
    void deleteTasksById(List<ID> ids);
    TaskResponseDTO markAsCompleted(ID id);
}
