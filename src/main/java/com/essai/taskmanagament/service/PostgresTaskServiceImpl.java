package com.essai.taskmanagament.service;

import com.essai.taskmanagament.TaskMapper;
import com.essai.taskmanagament.dto.TaskRequestDTO;
import com.essai.taskmanagament.dto.TaskResponseDTO;
import com.essai.taskmanagament.entity.PostgresTask;
import com.essai.taskmanagament.repository.TaskRepository;
import com.essai.taskmanagament.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Profile("postgres")
@RequiredArgsConstructor
public class PostgresTaskServiceImpl implements TaskService<PostgresTask, Long> {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO addTask(TaskRequestDTO taskRequestDTO) {
        PostgresTask task = taskMapper.toEntityFromRequestDTOForPostgres(taskRequestDTO);
        task.setCreatedAt(ZonedDateTime.now());
        task.setUpdatedAt(ZonedDateTime.now());
        PostgresTask savedTask = taskRepository.save(task);
        return taskMapper.toResponseDTO(savedTask);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        List<PostgresTask> tasks = taskRepository.findAll();
        return taskMapper.toResponseDTOListFromPostgres(tasks);
    }

    @Override
    public Optional<TaskResponseDTO> getTaskById(Long id) {
        Optional<PostgresTask> task = taskRepository.findById(id);
        return task.map(taskMapper::toResponseDTO);
    }

    @Override
    public Optional<TaskResponseDTO> getTaskByTitle(String title) {
        Optional<PostgresTask> task = taskRepository.findByTitle(title);
        return task.map(taskMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        PostgresTask existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found"));
        taskMapper.updateEntityFromRequestDTOForPostgres(taskRequestDTO, existingTask);
        existingTask.setUpdatedAt(ZonedDateTime.now());
        PostgresTask updatedTask = taskRepository.save(existingTask);
        return taskMapper.toResponseDTO(updatedTask);
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteTasksById(List<Long> ids) {
        taskRepository.deleteAllById(ids);
    }

    @Override
    @Transactional
    public TaskResponseDTO markAsCompleted(Long id) {
        PostgresTask task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found"));
        task.setCompleted(true);
        task.setUpdatedAt(ZonedDateTime.now());
        PostgresTask updatedTask = taskRepository.save(task);
        return taskMapper.toResponseDTO(updatedTask);
    }
}
