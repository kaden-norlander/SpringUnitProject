package com.example.unitprojectspring.Repositories;
import com.example.unitprojectspring.Entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    // Custom queries can be defined here if needed later
}
