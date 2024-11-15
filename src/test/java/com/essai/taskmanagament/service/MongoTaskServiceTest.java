package com.essai.taskmanagament.service;

import com.essai.taskmanagament.TaskMapper;
import com.essai.taskmanagament.dto.TaskRequestDTO;
import com.essai.taskmanagament.dto.TaskResponseDTO;
import com.essai.taskmanagament.entity.MongoTask;
import com.essai.taskmanagament.repository.MongoTaskRepository;
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
public class MongoTaskServiceTest {

    @Mock
    private MongoTaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private com.essai.taskmanagament.service.MongoTaskServiceImpl taskService;

    private TaskRequestDTO taskRequestDTO;
    private TaskResponseDTO taskResponseDTO;
    private MongoTask mongoTask;

    @BeforeEach
    public void setUp() {
        taskRequestDTO = Instancio.create(TaskRequestDTO.class);
        taskResponseDTO = Instancio.create(TaskResponseDTO.class);
        mongoTask = new MongoTask(
                "1L",
                taskResponseDTO.getTitle(),
                taskResponseDTO.getDescription(),
                ZonedDateTime.now(),
                taskResponseDTO.isCompleted()
        );
    }

    @Test
    public void testAddTask() {
        when(taskMapper.toEntityFromRequestDTOForMongo(taskRequestDTO)).thenReturn(mongoTask);
        when(taskRepository.save(mongoTask)).thenReturn(mongoTask);
        when(taskMapper.toResponseDTO(mongoTask)).thenReturn(taskResponseDTO);

        TaskResponseDTO result = taskService.addTask(taskRequestDTO);

        assertNotNull(result);
        assertEquals(taskResponseDTO.getTitle(), result.getTitle());
        verify(taskMapper, times(1)).toEntityFromRequestDTOForMongo(taskRequestDTO);
        verify(taskRepository, times(1)).save(mongoTask);
        verify(taskMapper, times(1)).toResponseDTO(mongoTask);
    }

    @Test
    public void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(mongoTask));
        when(taskMapper.toResponseDTOListFromMongo(List.of(mongoTask))).thenReturn(List.of(taskResponseDTO));

        List<TaskResponseDTO> result = taskService.getAllTasks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(taskResponseDTO.getTitle(), result.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
        verify(taskMapper, times(1)).toResponseDTOListFromMongo(List.of(mongoTask));
    }

    @Test
    public void testGetTaskById() {
        when(taskRepository.findById("1L")).thenReturn(Optional.of(mongoTask));
        when(taskMapper.toResponseDTO(mongoTask)).thenReturn(taskResponseDTO);

        Optional<TaskResponseDTO> result = taskService.getTaskById("1L");

        assertTrue(result.isPresent());
        assertEquals(taskResponseDTO.getTitle(), result.get().getTitle());
        verify(taskRepository, times(1)).findById("1L");
        verify(taskMapper, times(1)).toResponseDTO(mongoTask);
    }

    @Test
    public void testGetTaskByTitle() {
        when(taskRepository.findByTitle(taskResponseDTO.getTitle())).thenReturn(Optional.of(mongoTask));
        when(taskMapper.toResponseDTO(mongoTask)).thenReturn(taskResponseDTO);

        Optional<TaskResponseDTO> result = taskService.getTaskByTitle(taskResponseDTO.getTitle());

        assertTrue(result.isPresent());
        assertEquals(taskResponseDTO.getTitle(), result.get().getTitle());
        verify(taskRepository, times(1)).findByTitle(taskResponseDTO.getTitle());
        verify(taskMapper, times(1)).toResponseDTO(mongoTask);
    }

    @Test
    public void testUpdateTask() {
        when(taskRepository.findById("1L")).thenReturn(Optional.of(mongoTask));
        doAnswer(invocation -> {
            TaskRequestDTO dto = invocation.getArgument(0);
            MongoTask entity = invocation.getArgument(1);
            entity.setDescription(dto.getDescription());
            return null;
        }).when(taskMapper).updateEntityFromRequestDTOForMongo(taskRequestDTO, mongoTask);
        when(taskRepository.save(mongoTask)).thenReturn(mongoTask);
        when(taskMapper.toResponseDTO(mongoTask)).thenReturn(taskResponseDTO);

        taskRequestDTO.setDescription("Updated Description");
        taskResponseDTO.setDescription("Updated Description");

        TaskResponseDTO result = taskService.updateTask("1L", taskRequestDTO);

        assertNotNull(result);
        assertEquals("Updated Description", result.getDescription());
        verify(taskMapper, times(1)).updateEntityFromRequestDTOForMongo(taskRequestDTO, mongoTask);
        verify(taskRepository, times(1)).save(mongoTask);
    }

    @Test
    public void testDeleteTaskById() {
        doNothing().when(taskRepository).deleteById("1L");

        taskService.deleteTaskById("1L");

        verify(taskRepository, times(1)).deleteById("1L");
    }

    @Test
    public void testDeleteTasksById() {
        doNothing().when(taskRepository).deleteAllById(List.of("1L", "2L"));

        taskService.deleteTasksById(List.of("1L", "2L"));

        verify(taskRepository, times(1)).deleteAllById(List.of("1L", "2L"));
    }

}
