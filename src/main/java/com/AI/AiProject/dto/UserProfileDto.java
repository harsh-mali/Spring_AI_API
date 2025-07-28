package com.AI.AiProject.dto;

import com.AI.AiProject.model.Role;

public record UserProfileDto(
        Integer id,
        String username,
        String email,
        Role role
) {
}