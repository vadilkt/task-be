package com.essai.taskmanagament.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="task")
@Entity
public class PostgresTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Le titre est requis")
    @Column(unique = true)
    private String title;

    private String description;
    private ZonedDateTime dueDate;
    private boolean completed=false;

    @Column(updatable = false)
    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    public PostgresTask(long l, String test1, String testDescription, ZonedDateTime now, boolean b) {
    }


    @PrePersist
   public void prePersist(){
       this.createdAt=ZonedDateTime.now();
   }

}


