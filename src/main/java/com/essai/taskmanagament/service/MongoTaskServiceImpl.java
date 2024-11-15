package com.essai.taskmanagament.service;

import com.essai.taskmanagament.TaskMapper;
import com.essai.taskmanagament.dto.TaskRequestDTO;
import com.essai.taskmanagament.dto.TaskResponseDTO;
import com.essai.taskmanagament.entity.MongoTask;
import com.essai.taskmanagament.entity.PostgresTask;
import com.essai.taskmanagament.repository.MongoTaskRepository;
import com.essai.taskmanagament.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Profile("mongo")
@RequiredArgsConstructor
public class MongoTaskServiceImpl implements TaskService<MongoTask, String> {

    private final MongoTaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO addTask(TaskRequestDTO taskRequestDTO) {
        MongoTask task = taskMapper.toEntityFromRequestDTOForMongo(taskRequestDTO);
        task.setCreatedAt(ZonedDateTime.now());
        task.setUpdatedAt(ZonedDateTime.now());
        MongoTask savedTask = taskRepository.save(task);
        return taskMapper.toResponseDTO(savedTask);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        List<MongoTask> tasks = taskRepository.findAll();
        return taskMapper.toResponseDTOListFromMongo(tasks);
    }

    @Override
    public Optional<TaskResponseDTO> getTaskById(String id) {
        Optional<MongoTask> task = taskRepository.findById(id);
        return task.map(taskMapper::toResponseDTO);
    }

    @Override
    public Optional<TaskResponseDTO> getTaskByTitle(String title) {
        Optional<MongoTask> task = taskRepository.findByTitle(title);
        return task.map(taskMapper::toResponseDTO);
    }

    @Override
    public TaskResponseDTO updateTask(String id, TaskRequestDTO taskRequestDTO) {
        MongoTask existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La tâche avec l'ID " + id + " n'a pas été trouvée !"));

        taskMapper.updateEntityFromRequestDTOForMongo(taskRequestDTO, existingTask);
        existingTask.setUpdatedAt(ZonedDateTime.now());
        MongoTask updatedTask = taskRepository.save(existingTask);
        return taskMapper.toResponseDTO(updatedTask);
    }

    @Override
    public void deleteTaskById(String id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteTasksById(List<String> ids) {
        taskRepository.deleteAllById(ids);
    }

    @Override
    public TaskResponseDTO markAsCompleted(String id) {
        MongoTask task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La tâche avec l'ID " + id + " n'a pas été trouvée !"));
        task.setCompleted(true);
        task.setUpdatedAt(ZonedDateTime.now());
        MongoTask updatedTask = taskRepository.save(task);
        return taskMapper.toResponseDTO(updatedTask);
    }
}
