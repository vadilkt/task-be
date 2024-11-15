package com.essai.taskmanagament.controller;

import com.essai.taskmanagament.dto.TaskRequestDTO;
import com.essai.taskmanagament.dto.TaskResponseDTO;
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

class PostgresTaskControllerTest {

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
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setTitle("Test PostgresTask");

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setTitle("Test PostgresTask");

        when(taskService.addTask(any(TaskRequestDTO.class))).thenReturn(taskResponseDTO);

        ResponseEntity<TaskResponseDTO> response = taskController.addTask(taskRequestDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(taskResponseDTO, response.getBody());
        verify(taskService, times(1)).addTask(any(TaskRequestDTO.class));
    }

    @Test
    void testGetTasks() throws Exception {
        TaskResponseDTO task1 = new TaskResponseDTO();
        task1.setTitle("PostgresTask 1");
        TaskResponseDTO task2 = new TaskResponseDTO();
        task2.setTitle("PostgresTask 2");

        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        ResponseEntity<List<TaskResponseDTO>> response = taskController.getTasks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void testGetTaskByIdFound() throws Exception {
        TaskResponseDTO taskDTO = new TaskResponseDTO();
        taskDTO.setTitle("Found PostgresTask");

        when(taskService.getTaskById(anyLong())).thenReturn(Optional.of(taskDTO));

        ResponseEntity<TaskResponseDTO> response = taskController.getTaskById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskDTO, response.getBody());
    }

    @Test
    void testGetTaskByIdNotFound() throws Exception {
        when(taskService.getTaskById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<TaskResponseDTO> response = taskController.getTaskById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateTask() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setTitle("Updated PostgresTask");

        TaskResponseDTO updatedTaskDTO = new TaskResponseDTO();
        updatedTaskDTO.setTitle("Updated PostgresTask");

        when(taskService.updateTask(anyLong(), any(TaskRequestDTO.class))).thenReturn(updatedTaskDTO);

        ResponseEntity<TaskResponseDTO> response = taskController.updateTask(1L, taskRequestDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTaskDTO, response.getBody());
        verify(taskService, times(1)).updateTask(anyLong(), any(TaskRequestDTO.class));
    }

    @Test
    void testUpdateTaskNotFound() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();

        when(taskService.updateTask(anyLong(), any(TaskRequestDTO.class))).thenThrow(new IllegalArgumentException());

        ResponseEntity<TaskResponseDTO> response = taskController.updateTask(1L, taskRequestDTO);
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
        TaskResponseDTO completedTaskDTO = new TaskResponseDTO();
        completedTaskDTO.setTitle("Completed PostgresTask");

        when(taskService.markAsCompleted(anyLong())).thenReturn(completedTaskDTO);

        ResponseEntity<TaskResponseDTO> response = taskController.markAsCompleted(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(completedTaskDTO, response.getBody());
        verify(taskService, times(1)).markAsCompleted(anyLong());
    }

    @Test
    void testMarkAsCompletedNotFound() throws Exception {
        when(taskService.markAsCompleted(anyLong())).thenThrow(new IllegalArgumentException());

        ResponseEntity<TaskResponseDTO> response = taskController.markAsCompleted(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
