package com.example.springunitproject.service;

import com.example.springunitproject.dto.ProjectDTO;
import com.example.springunitproject.dto.SectionDTO;
import com.example.springunitproject.dto.TaskDTO;
import com.example.springunitproject.entities.Project;
import com.example.springunitproject.entities.Section;
import com.example.springunitproject.entities.User;
import com.example.springunitproject.repositories.ProjectRepository;
import com.example.springunitproject.repositories.UserRepository;
import com.example.springunitproject.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProjectDTO createProject(Project project, Long user_id) {
        if (user_id != null) {
            User user = userRepository.findById(user_id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + user_id));
            user.addProject(project);
            Project savedProject = projectRepository.save(project);
            return convertToDto(savedProject);
        } else {
            throw new IllegalArgumentException("User ID must not be null");
        }
    }

    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return convertToDto(project);
    }

    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProjectDTO updateProject(Long id, Project projectDetails) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        project.setTitle(projectDetails.getTitle());
        project.setDescription(projectDetails.getDescription());
        Project updatedProject = projectRepository.save(project);
        return convertToDto(updatedProject);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found");
        }
        projectRepository.deleteById(id);
    }

    private ProjectDTO convertToDto(Project projectEntity) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(projectEntity.getId());
        dto.setTitle(projectEntity.getTitle());
        dto.setDescription(projectEntity.getDescription());
        dto.setCompletionPercentage(projectEntity.getCompletionPercentage());

        if (projectEntity.getSections() != null) {
            dto.setSections(projectEntity.getSections().stream()
                    .map(this::convertSectionToDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private SectionDTO convertSectionToDto(Section sectionEntity) {
        SectionDTO dto = new SectionDTO();
        dto.setId(sectionEntity.getId());
        dto.setTitle(sectionEntity.getTitle());
        dto.setCompletionPercentage(sectionEntity.getCompletionPercentage());

        if (sectionEntity.getTasks() != null) {
            dto.setTasks(sectionEntity.getTasks().stream()
                    .map(task -> new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted()))
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
