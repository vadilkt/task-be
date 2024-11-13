package com.essai.taskmanagament.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TaskDTO {
    private Long id;

    @NotBlank(message = "le titre est requis")
    private String title;

    private String description;
    private ZonedDateTime dueDate;
    private boolean completed;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public TaskDTO(String title, String description, ZonedDateTime dueDate, boolean completed) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
    }
}
