package com.example.springunitproject.controller;

import com.example.springunitproject.dto.SectionDto;
import com.example.springunitproject.service.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public List<SectionDto> getAllSections() {
        return sectionService.findAllSections();
    }

    @GetMapping("/{id}")
    public SectionDto getSectionById(@PathVariable Long id) {
        return sectionService.findSectionById(id);
    }

    @PostMapping
    public ResponseEntity<SectionDto> createSection(@RequestBody SectionDto sectionDto) {
        return new ResponseEntity<>(sectionService.createSection(sectionDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public SectionDto updateSection(@PathVariable Long id, @RequestBody SectionDto sectionDto) {
        return sectionService.updateSection(id, sectionDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return ResponseEntity.noContent().build();
    }
}
