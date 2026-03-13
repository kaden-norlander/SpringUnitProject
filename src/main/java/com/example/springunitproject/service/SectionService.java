package com.example.springunitproject.service;

import com.example.springunitproject.dto.SectionDTO;
import com.example.springunitproject.dto.TaskDTO;
import com.example.springunitproject.entities.Project;
import com.example.springunitproject.entities.Section;
import com.example.springunitproject.repositories.ProjectRepository;
import com.example.springunitproject.repositories.SectionRepository;
import com.example.springunitproject.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final ProjectRepository projectRepository;

    public SectionService(SectionRepository sectionRepository, ProjectRepository projectRepository) {
        this.sectionRepository = sectionRepository;
        this.projectRepository = projectRepository;
    }

    public SectionDTO createSection(Section section, Long project_id) {
        if (project_id != null) {
            Project project = projectRepository.findById(project_id)
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + project_id));
            project.addSection(section);
            Section savedSection = sectionRepository.save(section);
            return convertToDto(savedSection);
        } else {
            throw new IllegalArgumentException("Project ID must not be null");
        }
    }

    public SectionDTO getSectionById(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        return convertToDto(section);
    }

    public List<SectionDTO> getAllSectionsByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return project.getSections().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SectionDTO updateSection(Long id, Section sectionDetails) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        section.setTitle(sectionDetails.getTitle());
        Section updatedSection = sectionRepository.save(section);
        return convertToDto(updatedSection);
    }

    public void deleteSection(Long id) {
        if (!sectionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Section not found");
        }
        sectionRepository.deleteById(id);
    }

    private SectionDTO convertToDto(Section sectionEntity) {
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
