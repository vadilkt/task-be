package com.essai.taskmanagament.repository;

import com.essai.taskmanagament.entity.MongoTask;
import com.essai.taskmanagament.entity.PostgresTask;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("mongo")
@EnableMongoRepositories
public interface MongoTaskRepository extends MongoRepository<MongoTask, String> {
    @Override
    Optional<MongoTask> findById(String id);
    Optional<MongoTask> findByTitle(String title);
}
