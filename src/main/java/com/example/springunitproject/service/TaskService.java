package com.example.springunitproject.service;

import com.example.springunitproject.dto.TaskDto;
import com.example.springunitproject.exception.ResourceNotFoundException;
import com.example.springunitproject.model.Section;
import com.example.springunitproject.model.Task;
import com.example.springunitproject.repository.SectionRepository;
import com.example.springunitproject.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<TaskDto> findAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TaskDto findTaskById(Long id) {
        return taskRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        Section section = sectionRepository.findById(taskDto.sectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + taskDto.sectionId()));
        Task task = new Task(taskDto.title(), taskDto.description(), taskDto.dueDate());
        section.addTask(task);
        return mapToDto(taskRepository.save(task));
    }

    @Transactional
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setDueDate(taskDto.dueDate());
        return mapToDto(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    private TaskDto mapToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getSection() != null ? task.getSection().getId() : null
        );
    }
}
