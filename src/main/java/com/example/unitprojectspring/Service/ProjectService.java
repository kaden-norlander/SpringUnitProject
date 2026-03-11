package com.example.unitprojectspring.Service;
import com.example.unitprojectspring.DTO.ProjectDTO;
import com.example.unitprojectspring.DTO.SectionDTO;
import com.example.unitprojectspring.Entities.Project;
import com.example.unitprojectspring.Entities.Section;
import com.example.unitprojectspring.Entities.User;
import com.example.unitprojectspring.Repositories.ProjectRepository;
import com.example.unitprojectspring.Repositories.UserRepository;
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
        Project savedProject = projectRepository.save(project);

        if (user_id != null) {
            Optional<User> userOpt = userRepository.findById(user_id);
            if (userOpt.isPresent()) {
                User user = userOpt.get();

                user.addProject(savedProject);

                userRepository.save(user);
            }
        }

        return convertToDto(savedProject);
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
