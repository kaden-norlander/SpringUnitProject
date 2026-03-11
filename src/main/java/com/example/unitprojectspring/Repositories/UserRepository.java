package com.example.unitprojectspring.Repositories;
import com.example.unitprojectspring.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom queries can be defined here if needed later
}

