package com.essai.taskmanagament;

import com.essai.taskmanagament.dto.TaskRequestDTO;
import com.essai.taskmanagament.dto.TaskResponseDTO;
import com.essai.taskmanagament.entity.MongoTask;
import com.essai.taskmanagament.entity.PostgresTask;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponseDTO toResponseDTO(PostgresTask postgresTask);
    List<TaskResponseDTO> toResponseDTOListFromPostgres(List<PostgresTask> postgresTasks);
    PostgresTask toEntityFromRequestDTOForPostgres(TaskRequestDTO taskRequestDTO);
    void updateEntityFromRequestDTOForPostgres(TaskRequestDTO taskRequestDTO, @MappingTarget PostgresTask postgresTask);

    TaskResponseDTO toResponseDTO(MongoTask mongoTask);
    List<TaskResponseDTO> toResponseDTOListFromMongo(List<MongoTask> mongoTasks);
    MongoTask toEntityFromRequestDTOForMongo(TaskRequestDTO taskRequestDTO);
    void updateEntityFromRequestDTOForMongo(TaskRequestDTO taskRequestDTO, @MappingTarget MongoTask mongoTask);
}