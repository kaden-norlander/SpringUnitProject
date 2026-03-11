package com.example.unitprojectspring.Service;
import com.example.unitprojectspring.DTO.ProjectDTO;
import com.example.unitprojectspring.DTO.SectionDTO;
import com.example.unitprojectspring.Entities.Project;
import com.example.unitprojectspring.Entities.Section;
import com.example.unitprojectspring.Entities.User;
import com.example.unitprojectspring.Repositories.ProjectRepository;
import com.example.unitprojectspring.Repositories.UserRepository;
import com.example.unitprojectspring.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            project.setUser(user);
        }
        Project savedProject = projectRepository.save(project);
        return convertToDto(savedProject);
    }

    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return convertToDto(project);
    }

    public java.util.List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(java.util.stream.Collectors.toList());
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
        return dto;
    }
}
