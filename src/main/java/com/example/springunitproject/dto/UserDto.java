package com.example.springunitproject.dto;

import java.util.List;

public record UserDto(
    Long id,
    String name,
    String email,
    List<Long> projectIds
) {}
