package com.example.springunitproject;

import com.example.springunitproject.model.Project;
import com.example.springunitproject.model.Section;
import com.example.springunitproject.model.Task;
import com.example.springunitproject.model.User;
import com.example.springunitproject.repository.ProjectRepository;
import com.example.springunitproject.repository.SectionRepository;
import com.example.springunitproject.repository.TaskRepository;
import com.example.springunitproject.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final SectionRepository sectionRepository;
    private final TaskRepository taskRepository;

    public DataInitializer(UserRepository userRepository, ProjectRepository projectRepository,
                           SectionRepository sectionRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.sectionRepository = sectionRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user = new User("John Doe", "john.doe@example.com");
            userRepository.save(user);

            Project project = new Project("Spring Project", "A project to demonstrate Spring Boot features.");
            user.addProject(project);
            projectRepository.save(project);

            Section section = new Section("Backend");
            project.addSection(section);
            sectionRepository.save(section);

            Task task1 = new Task("Setup Entities", "Create JPA entities for User, Project, Section, and Task", LocalDate.now().plusDays(1));
            section.addTask(task1);
            taskRepository.save(task1);

            Task task2 = new Task("Setup Repositories", "Create JpaRepository interfaces", LocalDate.now().plusDays(2));
            section.addTask(task2);
            taskRepository.save(task2);

            System.out.println("Sample data initialized.");
        }
    }
}
