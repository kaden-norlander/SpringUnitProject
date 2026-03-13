package com.example.springunitproject.service;

import com.example.springunitproject.dto.TaskDTO;
import com.example.springunitproject.entities.Section;
import com.example.springunitproject.entities.Task;
import com.example.springunitproject.repositories.SectionRepository;
import com.example.springunitproject.repositories.TaskRepository;
import com.example.springunitproject.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final SectionRepository sectionRepository;

    public TaskService(TaskRepository taskRepository, SectionRepository sectionRepository) {
        this.taskRepository = taskRepository;
        this.sectionRepository = sectionRepository;
    }

    public TaskDTO createTask(Task task, Long section_id) {
        if (section_id != null) {
            Section section = sectionRepository.findById(section_id)
                    .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + section_id));
            section.addTask(task);
            Task savedTask = taskRepository.save(task);
            return convertToDto(savedTask);
        } else {
            throw new IllegalArgumentException("Section ID must not be null");
        }
    }

    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return convertToDto(task);
    }

    public List<TaskDTO> getAllTasksBySection(Long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        return section.getTasks().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TaskDTO updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());
        Task updatedTask = taskRepository.save(task);
        return convertToDto(updatedTask);
    }

    public TaskDTO toggleTaskCompletion(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setCompleted(!task.isCompleted());
        Task updatedTask = taskRepository.save(task);
        return convertToDto(updatedTask);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found");
        }
        taskRepository.deleteById(id);
    }

    private TaskDTO convertToDto(Task taskEntity) {
        TaskDTO dto = new TaskDTO();
        dto.setId(taskEntity.getId());
        dto.setTitle(taskEntity.getTitle());
        dto.setDescription(taskEntity.getDescription());
        dto.setCompleted(taskEntity.isCompleted());
        return dto;
    }
}
