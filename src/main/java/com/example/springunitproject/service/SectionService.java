package com.example.springunitproject.service;

import com.example.springunitproject.dto.SectionDto;
import com.example.springunitproject.exception.ResourceNotFoundException;
import com.example.springunitproject.model.Project;
import com.example.springunitproject.model.Section;
import com.example.springunitproject.model.Task;
import com.example.springunitproject.repository.ProjectRepository;
import com.example.springunitproject.repository.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<SectionDto> findAllSections() {
        return sectionRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public SectionDto findSectionById(Long id) {
        return sectionRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + id));
    }

    @Transactional
    public SectionDto createSection(SectionDto sectionDto) {
        Project project = projectRepository.findById(sectionDto.projectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + sectionDto.projectId()));
        Section section = new Section(sectionDto.name());
        project.addSection(section);
        return mapToDto(sectionRepository.save(section));
    }

    @Transactional
    public SectionDto updateSection(Long id, SectionDto sectionDto) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + id));
        section.setName(sectionDto.name());
        return mapToDto(sectionRepository.save(section));
    }

    @Transactional
    public void deleteSection(Long id) {
        if (!sectionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Section not found with id: " + id);
        }
        sectionRepository.deleteById(id);
    }

    private SectionDto mapToDto(Section section) {
        return new SectionDto(
                section.getId(),
                section.getName(),
                section.getProject() != null ? section.getProject().getId() : null,
                section.getTasks().stream().map(Task::getId).collect(Collectors.toList())
        );
    }
}
