package com.example.springunitproject.service;

import com.example.springunitproject.dto.ProjectDto;
import com.example.springunitproject.exception.ResourceNotFoundException;
import com.example.springunitproject.model.Project;
import com.example.springunitproject.model.Section;
import com.example.springunitproject.model.User;
import com.example.springunitproject.repository.ProjectRepository;
import com.example.springunitproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<ProjectDto> findAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ProjectDto findProjectById(Long id) {
        return projectRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
    }

    @Transactional
    public ProjectDto createProject(ProjectDto projectDto) {
        User user = userRepository.findById(projectDto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + projectDto.userId()));
        Project project = new Project(projectDto.name(), projectDto.description());
        user.addProject(project);
        return mapToDto(projectRepository.save(project));
    }

    @Transactional
    public ProjectDto updateProject(Long id, ProjectDto projectDto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        project.setName(projectDto.name());
        project.setDescription(projectDto.description());
        return mapToDto(projectRepository.save(project));
    }

    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }

    private ProjectDto mapToDto(Project project) {
        return new ProjectDto(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwner() != null ? project.getOwner().getId() : null,
                project.getSections().stream().map(Section::getId).collect(Collectors.toList())
        );
    }
}
