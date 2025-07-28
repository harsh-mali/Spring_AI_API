package com.AI.AiProject.service;

import com.AI.AiProject.model.User;
import com.AI.AiProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // --- ADD THIS NEW METHOD ---
    @Transactional
    public void deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
    // -------------------------
}
