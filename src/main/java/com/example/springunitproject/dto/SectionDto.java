package com.example.springunitproject.dto;

import java.util.List;

public record SectionDto(
    Long id,
    String name,
    Long projectId,
    List<Long> taskIds
) {}
