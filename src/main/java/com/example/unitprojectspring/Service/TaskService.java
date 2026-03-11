package com.example.unitprojectspring.Service;
import com.example.unitprojectspring.DTO.TaskDTO;
import com.example.unitprojectspring.Entities.Section;
import com.example.unitprojectspring.Entities.Task;
import com.example.unitprojectspring.Repositories.SectionRepository;
import com.example.unitprojectspring.Repositories.TaskRepository;
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
        Task savedTask = taskRepository.save(task);

        if (section_id != null) {
            Optional<Section> sectionOpt = sectionRepository.findById(section_id);
            if (sectionOpt.isPresent()) {
                Section section = sectionOpt.get();

                section.addTask(savedTask);

                sectionRepository.save(section);
            }
        }

        return convertToDto(savedTask);
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
