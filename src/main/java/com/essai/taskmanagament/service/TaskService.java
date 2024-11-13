package com.essai.taskmanagament.service;

import com.essai.taskmanagament.TaskMapper;
import com.essai.taskmanagament.dto.TaskDTO;
import com.essai.taskmanagament.entity.Task;
import com.essai.taskmanagament.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


@AllArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    //créer une tache
   public TaskDTO addTask(TaskDTO taskDTO){
       taskDTO.setCreatedAt(ZonedDateTime.now());
       Task task = taskMapper.toTask(taskDTO);
       taskRepository.save(task);
       return taskMapper.toTaskDTO(task);
   }

   //récupérer une liste de taches
   public List<TaskDTO> getAllTasks(){
       List<Task> tasks = taskRepository.findAll();
   return taskMapper.toDTOList(tasks);
   }

   //récupérer une tâche par son id
    public Optional<TaskDTO> getTaskById(Long id){
       Optional<Task> task = taskRepository.findById(id);
       return task.map(taskMapper::toTaskDTO);
    }

    //récupérer une tâche par son title
    public Optional<TaskDTO> getTaskByTitle(String title){
       Optional<Task> task = taskRepository.findByTitle(title);
       return task.map(taskMapper::toTaskDTO);
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO){
        Optional<Task> taskOptional = taskRepository.findById(id);

        if(taskOptional.isPresent()){
            Task task = taskOptional.get();

            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setDueDate(taskDTO.getDueDate());
            task.setCompleted(taskDTO.isCompleted());
            task.setUpdatedAt(ZonedDateTime.now());
            task = taskRepository.save(task);
            return taskMapper.toTaskDTO(task);
        } else {
            throw new IllegalArgumentException("La tâche avec l'ID "+ id+" n'a pas été trouvée!");
        }
    }

    public void deleteTaskById(Long id){
       taskRepository.deleteById(id);
    }

    public void deleteTasksById(List<Long> ids){
       taskRepository.deleteAllById(ids);
    }

    @Transactional
    public TaskDTO markAsCompleted(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setCompleted(true);
            task.setUpdatedAt(ZonedDateTime.now());  // Update the timestamp
            task = taskRepository.save(task);
            return taskMapper.toTaskDTO(task);
        } else {
            throw new IllegalArgumentException("La tâche avec l'ID " + id + " n'a pas été trouvée !");
        }
    }




}
