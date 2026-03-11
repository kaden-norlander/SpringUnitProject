package com.example.springunitproject.dto;

import java.time.LocalDate;

public record TaskDto(
    Long id,
    String title,
    String description,
    LocalDate dueDate,
    Long sectionId
) {}
