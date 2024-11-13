package com.essai.taskmanagament.controller;

import com.essai.taskmanagament.dto.TaskDTO;
import com.essai.taskmanagament.service.TaskService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private TaskDTO taskDTO;

    @BeforeEach
    public void setUp() {
        taskDTO = new TaskDTO("Test1", "Test Description", ZonedDateTime.now(), false);
    }

    @Test
    public void testAddTask() throws Exception {
        when(taskService.addTask(any(TaskDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Test1\", \"description\": \"Test Description\", \"completed\": false }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test1"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    public void testGetAllTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(List.of(taskDTO));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Test1"));
    }

    @Test
    public void testGetTaskById() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(taskDTO));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test1"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        taskDTO.setDescription("Updated Description");
        when(taskService.updateTask(eq(1L), any(TaskDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Test1\", \"description\": \"Updated Description\", \"completed\": false }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    public void testDeleteTaskById() throws Exception {
        doNothing().when(taskService).deleteTaskById(1L);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTaskById(1L);
    }
}

