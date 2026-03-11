package com.example.unitprojectspring.Service;
import com.example.unitprojectspring.DTO.TaskDTO;
import com.example.unitprojectspring.Entities.Section;
import com.example.unitprojectspring.Entities.Task;
import com.example.unitprojectspring.Repositories.SectionRepository;
import com.example.unitprojectspring.Repositories.TaskRepository;
import com.example.unitprojectspring.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                    .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
            task.setSection(section);
        }
        Task savedTask = taskRepository.save(task);
        return convertToDto(savedTask);
    }

    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return convertToDto(task);
    }

    public java.util.List<TaskDTO> getAllTasksBySection(Long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        return section.getTasks().stream()
                .map(this::convertToDto)
                .collect(java.util.stream.Collectors.toList());
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
