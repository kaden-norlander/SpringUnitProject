package com.example.springunitproject.controller;

import com.example.springunitproject.dto.ProjectDTO;
import com.example.springunitproject.dto.UserRegistrationDTO;
import com.example.springunitproject.service.ProjectService;
import com.example.springunitproject.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;
    private final ProjectService projectService;

    public HomeController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegistrationDTO registrationDTO) {
        userService.registerUser(registrationDTO);
        return "redirect:/login?success";
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = auth.getName();
        
        // Fetch all projects for simplicity for now, or filter by user if userService provides it
        List<ProjectDTO> allProjects = projectService.getAllProjects();
        model.addAttribute("projects", allProjects);
        return "projects";
    }
}
