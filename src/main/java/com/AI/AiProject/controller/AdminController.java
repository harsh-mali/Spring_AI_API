package com.AI.AiProject.controller;

import com.AI.AiProject.model.User;
import com.AI.AiProject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*; // Import more annotations

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin // Allow requests from our frontend
@PreAuthorize("hasRole('ADMIN')") // Secures all methods in this controller
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // --- ADD THIS NEW ENDPOINT ---
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.noContent().build(); // Standard response for successful deletion
    }
    // --------------------------
}
