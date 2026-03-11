package com.example.springunitproject.dto;

import java.util.List;

public record ProjectDto(
    Long id,
    String name,
    String description,
    Long userId,
    List<Long> sectionIds
) {}
