package com.essai.taskmanagament.entity;

import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
public class MongoTask {
    @Id
    private String id;

    @Indexed(unique = true)
    private String title;
    private String description;
    private ZonedDateTime dueDate;
    private boolean completed=false;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public MongoTask(String l, String title, String description, ZonedDateTime now, boolean completed) {
    }
}
