package com.essai.taskmanagament.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="task")
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Le titre est requis")
    @Column(unique = true)
    private String title;

    private String description;
    private ZonedDateTime dueDate;
    private boolean completed=false;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public Task(Long id, String title, String description, ZonedDateTime dueDate, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
    }
}
