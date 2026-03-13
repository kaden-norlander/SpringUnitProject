package com.example.unitprojectspring.Controllers;
import com.example.unitprojectspring.Entities.Project;
import com.example.unitprojectspring.Service.UserService;
import org.springframework.ui.Model;
import com.example.unitprojectspring.DTO.ProjectDTO;
import com.example.unitprojectspring.Entities.User;
import com.example.unitprojectspring.Service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/dashboard")
public class HomeController {

    private final UserService userService;
    private final ProjectService projectService;

    public HomeController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("")
    public String getAllProjects(Model model, Principal principal) {
        User currentUser = userService.getUserFromPrincipal(principal.getName());
        List<ProjectDTO> projects = projectService.getAllProjectsWithUserId(currentUser.getId());
        model.addAttribute("projects", projects);
        return "dashboard";
    }

    @PostMapping("/{project_id}/delete")
    public String deleteProject(@PathVariable Long project_id, Principal principal){
        User currentUser = userService.getUserFromPrincipal(principal.getName());
        ProjectDTO projectToDelete = projectService.getProjectById(project_id);

        if (!projectToDelete.getUserId().equals(currentUser.getId())) {
            return "redirect:/api/dashboard?error=unauthorized";
        }

        projectService.deleteProject(project_id);

        return "redirect:/api/dashboard";
    }

    @PostMapping("/add")
    public String addProject(@ModelAttribute Project newproject, Principal principal){
        User currentUser = userService.getUserFromPrincipal(principal.getName());
        projectService.createProject(newproject, currentUser.getId());

        // FIXED: Redirect back to the GET mapping to reload the data!
        return "redirect:/api/dashboard";
    }
}