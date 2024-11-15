package com.essai.taskmanagament.repository;

import com.essai.taskmanagament.entity.PostgresTask;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("postgres")
public interface TaskRepository extends JpaRepository<PostgresTask, Long> {
    Optional<PostgresTask> findByTitle(String title);
}
