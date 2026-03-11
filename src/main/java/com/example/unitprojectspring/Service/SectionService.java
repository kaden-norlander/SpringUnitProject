package com.example.unitprojectspring.Service;
import com.example.unitprojectspring.DTO.SectionDTO;
import com.example.unitprojectspring.Entities.Project;
import com.example.unitprojectspring.Entities.Section;
import com.example.unitprojectspring.Repositories.ProjectRepository;
import com.example.unitprojectspring.Repositories.SectionRepository;
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
        Section savedSection = sectionRepository.save(section);

        if (project_id != null) {
            Optional<Project> projectOpt = projectRepository.findById(project_id);
            if (projectOpt.isPresent()) {
                Project project = projectOpt.get();

                project.addSection(savedSection);

                projectRepository.save(project);
            }
        }

        return convertToDto(savedSection);
    }

    private SectionDTO convertToDto(Section sectionEntity) {
        SectionDTO dto = new SectionDTO();
        dto.setId(sectionEntity.getId());
        dto.setTitle(sectionEntity.getTitle());
        dto.setCompletionPercentage(sectionEntity.getCompletionPercentage());
        return dto;
    }
}
