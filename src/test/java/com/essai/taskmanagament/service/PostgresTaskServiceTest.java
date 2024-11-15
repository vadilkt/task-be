package com.essai.taskmanagament.service;

import com.essai.taskmanagament.TaskMapper;
import com.essai.taskmanagament.dto.TaskRequestDTO;
import com.essai.taskmanagament.dto.TaskResponseDTO;
import com.essai.taskmanagament.entity.PostgresTask;
import com.essai.taskmanagament.repository.TaskRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostgresTaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private com.essai.taskmanagament.service.PostgresTaskServiceImpl taskService;

    private TaskRequestDTO taskRequestDTO;
    private TaskResponseDTO taskResponseDTO;
    private PostgresTask postgresTask;

    @BeforeEach
    public void setUp() {
        taskRequestDTO = Instancio.create(TaskRequestDTO.class);
        taskResponseDTO = Instancio.create(TaskResponseDTO.class);
        postgresTask = new PostgresTask(1L, taskResponseDTO.getTitle(), taskResponseDTO.getDescription(), ZonedDateTime.now(), taskResponseDTO.isCompleted());}

    @Test
    public void testAddTask() {
        when(taskMapper.toEntityFromRequestDTOForPostgres(taskRequestDTO)).thenReturn(postgresTask);
        when(taskMapper.toResponseDTO(postgresTask)).thenReturn(taskResponseDTO);
        when(taskRepository.save(postgresTask)).thenReturn(postgresTask);

        TaskResponseDTO result = taskService.addTask(taskRequestDTO);

        assertNotNull(result);
        assertEquals(taskResponseDTO.getTitle(), result.getTitle());
        verify(taskRepository, times(1)).save(postgresTask);
    }

    @Test
    public void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(postgresTask));
        when(taskMapper.toResponseDTOListFromPostgres(List.of(postgresTask))).thenReturn(List.of(taskResponseDTO));

        List<TaskResponseDTO> result = taskService.getAllTasks();

        assertEquals(1, result.size());
        assertEquals(taskResponseDTO.getTitle(), result.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(postgresTask));
        when(taskMapper.toResponseDTO(postgresTask)).thenReturn(taskResponseDTO);

        Optional<TaskResponseDTO> result = taskService.getTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals(taskResponseDTO.getTitle(), result.get().getTitle());
    }

    @Test
    public void testGetTaskByTitle() {
        when(taskRepository.findByTitle(taskResponseDTO.getTitle())).thenReturn(Optional.of(postgresTask));
        when(taskMapper.toResponseDTO(postgresTask)).thenReturn(taskResponseDTO);

        Optional<TaskResponseDTO> result = taskService.getTaskByTitle(taskResponseDTO.getTitle());

        assertTrue(result.isPresent());
        assertEquals(taskResponseDTO.getTitle(), result.get().getTitle());
        verify(taskRepository, times(1)).findByTitle(taskResponseDTO.getTitle());
    }

    @Test
    public void testUpdateTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(postgresTask));
        doAnswer(invocation -> {
            TaskRequestDTO dto = invocation.getArgument(0);
            PostgresTask t = invocation.getArgument(1);
            t.setDescription(dto.getDescription());
            return null;
        }).when(taskMapper).updateEntityFromRequestDTOForPostgres(taskRequestDTO, postgresTask);
        when(taskRepository.save(postgresTask)).thenReturn(postgresTask);
        when(taskMapper.toResponseDTO(postgresTask)).thenReturn(taskResponseDTO);

        taskRequestDTO.setDescription("Updated Description");
        taskResponseDTO.setDescription("Updated Description");

        TaskResponseDTO result = taskService.updateTask(1L, taskRequestDTO);

        assertEquals("Updated Description", result.getDescription());
        verify(taskMapper, times(1)).updateEntityFromRequestDTOForPostgres(taskRequestDTO, postgresTask);
        verify(taskRepository, times(1)).save(postgresTask);
    }
    @Test
    public void testDeleteTaskById() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTaskById(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteTasksById() {
        doNothing().when(taskRepository).deleteAllById(List.of(1L, 2L));

        taskService.deleteTasksById(List.of(1L, 2L));

        verify(taskRepository, times(1)).deleteAllById(List.of(1L, 2L));
    }


}