package com.example.unitprojectspring.Repositories;
import com.example.unitprojectspring.Entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Custom queries can be defined here if needed later
}

