package com.example.unitprojectspring.DTO;

import java.util.ArrayList;
import java.util.List;

public class SectionDTO {
    private Long id;
    private String title;
    private int completionPercentage; // Calculated value from the entity
    private List<TaskDTO> tasks = new ArrayList<>();

    public SectionDTO() {}

    public SectionDTO(Long id, String title, int completionPercentage) {
        this.id = id;
        this.title = title;
        this.completionPercentage = completionPercentage;
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCompletionPercentage() { return completionPercentage; }
    public void setCompletionPercentage(int completionPercentage) { this.completionPercentage = completionPercentage; }

    public List<TaskDTO> getTasks() { return tasks; }
    public void setTasks(List<TaskDTO> tasks) { this.tasks = tasks; }
}
