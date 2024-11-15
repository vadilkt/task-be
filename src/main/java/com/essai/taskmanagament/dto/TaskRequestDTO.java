package com.essai.taskmanagament.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
public class TaskRequestDTO {
    @NotBlank(message="le titre est requis !")
    private String title;
    private String description;
    private ZonedDateTime dueDate;
}
