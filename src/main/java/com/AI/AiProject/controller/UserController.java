package com.AI.AiProject.controller;

import com.AI.AiProject.dto.UserProfileDto;
import com.AI.AiProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin // Allow requests from our frontend
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getCurrentUserProfile(Principal principal) {
        // Get the email of the currently logged-in user from the security context
        String userEmail = principal.getName();

        // Call the service to get the user's profile data
        UserProfileDto userProfile = userService.getUserProfile(userEmail);

        return ResponseEntity.ok(userProfile);
    }
}