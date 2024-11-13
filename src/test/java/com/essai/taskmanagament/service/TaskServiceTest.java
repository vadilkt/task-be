package com.essai.taskmanagament.service;


import com.essai.taskmanagament.TaskMapper;
import com.essai.taskmanagament.dto.TaskDTO;
import com.essai.taskmanagament.entity.Task;
import com.essai.taskmanagament.repository.TaskRepository;
import com.essai.taskmanagament.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    TaskDTO taskDTO;
    Task task;
    @BeforeEach
    public void setUp(){
        taskDTO = new TaskDTO("Test1", "Test Description",ZonedDateTime.now(), false );
        task = new Task(1L, "Test1", "Test Description", ZonedDateTime.now(), false);
    }

    @Test
    public void testAddTask(){
        when(taskMapper.toTask(taskDTO)).thenReturn(task);
        when(taskMapper.toTaskDTO(task)).thenReturn(taskDTO);
        when(taskRepository.save(task)).thenReturn(task);

        TaskDTO result = taskService.addTask(taskDTO);

        assertNotNull(result);
        assertEquals("Test1", result.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testGetAllTask(){
        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(taskMapper.toDTOList(List.of(task))).thenReturn(List.of(taskDTO));

        List<TaskDTO> result = taskService.getAllTasks();

        assertEquals(1, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testGetTaskById(){
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toTaskDTO(task)).thenReturn(taskDTO);

        Optional<TaskDTO> result = taskService.getTaskById(1L);
        assertTrue(result.isPresent());
        assertEquals("Test1", result.get().getTitle());
    }

    @Test
    public void TestUpdate(){
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toTaskDTO(task)).thenReturn(taskDTO);

        taskDTO.setDescription("Updated Description");
        TaskDTO result = taskService.updateTask(1L, taskDTO);

        assertEquals("Updated Description", result.getDescription());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testDeleteTaskById() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTaskById(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}
