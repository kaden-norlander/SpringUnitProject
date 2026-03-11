package com.example.unitprojectspring.Service;
import com.example.unitprojectspring.DTO.SectionDTO;
import com.example.unitprojectspring.Entities.Project;
import com.example.unitprojectspring.Entities.Section;
import com.example.unitprojectspring.Repositories.ProjectRepository;
import com.example.unitprojectspring.Repositories.SectionRepository;
import com.example.unitprojectspring.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
            section.setProject(project);
        }
        Section savedSection = sectionRepository.save(section);
        return convertToDto(savedSection);
    }

    public SectionDTO getSectionById(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        return convertToDto(section);
    }

    public java.util.List<SectionDTO> getAllSectionsByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return project.getSections().stream()
                .map(this::convertToDto)
                .collect(java.util.stream.Collectors.toList());
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
        return dto;
    }
}
