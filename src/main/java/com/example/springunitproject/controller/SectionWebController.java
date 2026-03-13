package com.example.springunitproject.controller;

import com.example.springunitproject.entities.Section;
import com.example.springunitproject.service.SectionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects/sections")
public class SectionWebController {

    private final SectionService sectionService;

    public SectionWebController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/add")
    public String addSection(@RequestParam Long projectId, @RequestParam String title) {
        Section section = new Section(title);
        sectionService.createSection(section, projectId);
        return "redirect:/projects";
    }

    @PostMapping("/update")
    public String updateSection(@RequestParam Long sectionId, @RequestParam String title) {
        Section sectionDetails = new Section(title);
        sectionService.updateSection(sectionId, sectionDetails);
        return "redirect:/projects";
    }

    @PostMapping("/delete")
    public String deleteSection(@RequestParam Long sectionId) {
        sectionService.deleteSection(sectionId);
        return "redirect:/projects";
    }
}
