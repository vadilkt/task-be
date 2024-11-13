package com.essai.taskmanagament.controller;

import com.essai.taskmanagament.dto.TaskDTO;
import com.essai.taskmanagament.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void testAddTask() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");

        when(taskService.addTask(any(TaskDTO.class))).thenReturn(taskDTO);

        ResponseEntity<TaskDTO> response = taskController.addTask(taskDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(taskDTO, response.getBody());
        verify(taskService, times(1)).addTask(any(TaskDTO.class));
    }

    @Test
    void testGetTasks() throws Exception {
        TaskDTO task1 = new TaskDTO();
        task1.setTitle("Task 1");
        TaskDTO task2 = new TaskDTO();
        task2.setTitle("Task 2");

        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        ResponseEntity<List<TaskDTO>> response = taskController.getTasks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void testGetTaskByIdFound() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Found Task");

        when(taskService.getTaskById(anyLong())).thenReturn(Optional.of(taskDTO));

        ResponseEntity<TaskDTO> response = taskController.getTaskById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskDTO, response.getBody());
    }

    @Test
    void testGetTaskByIdNotFound() throws Exception {
        when(taskService.getTaskById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<TaskDTO> response = taskController.getTaskById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateTask() throws Exception {
        TaskDTO updatedTaskDTO = new TaskDTO();
        updatedTaskDTO.setTitle("Updated Task");

        when(taskService.updateTask(anyLong(), any(TaskDTO.class))).thenReturn(updatedTaskDTO);

        ResponseEntity<TaskDTO> response = taskController.updateTask(1L, updatedTaskDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTaskDTO, response.getBody());
        verify(taskService, times(1)).updateTask(anyLong(), any(TaskDTO.class));
    }

    @Test
    void testUpdateTaskNotFound() throws Exception {
        TaskDTO taskDTO = new TaskDTO();

        when(taskService.updateTask(anyLong(), any(TaskDTO.class))).thenThrow(new IllegalArgumentException());

        ResponseEntity<TaskDTO> response = taskController.updateTask(1L, taskDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteTaskById() throws Exception {
        doNothing().when(taskService).deleteTaskById(anyLong());

        ResponseEntity<Void> response = taskController.deleteTask(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTaskById(anyLong());
    }

    @Test
    void testDeleteTasks() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L);
        doNothing().when(taskService).deleteTasksById(anyList());

        ResponseEntity<Void> response = taskController.deleteTasks(ids);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTasksById(anyList());
    }

    @Test
    void testMarkAsCompleted() throws Exception {
        TaskDTO completedTaskDTO = new TaskDTO();
        completedTaskDTO.setTitle("Completed Task");

        when(taskService.markAsCompleted(anyLong())).thenReturn(completedTaskDTO);

        ResponseEntity<TaskDTO> response = taskController.markAsCompleted(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(completedTaskDTO, response.getBody());
        verify(taskService, times(1)).markAsCompleted(anyLong());
    }

    @Test
    void testMarkAsCompletedNotFound() throws Exception {
        when(taskService.markAsCompleted(anyLong())).thenThrow(new IllegalArgumentException());

        ResponseEntity<TaskDTO> response = taskController.markAsCompleted(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
