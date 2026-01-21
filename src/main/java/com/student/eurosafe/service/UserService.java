package com.student.eurosafe.service;

import com.student.eurosafe.entity.User;
import com.student.eurosafe.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // Import the Shredder
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor Injection: We ask Spring for the Repository AND the PasswordEncoder
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        // STEP 1: VALIDATION (Check if username exists)
        // ---------------------------------------------------------------
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        
        if (existingUser.isPresent()) {
            // Throw error if duplicate found (caught by GlobalExceptionHandler)
            throw new RuntimeException("Username '" + user.getUsername() + "' is already taken!");
        }

        // STEP 2: ENCRYPTION (The Security Upgrade)
        // ---------------------------------------------------------------
        // Get the plain text password (e.g., "password123")
        String plainPassword = user.getPasswordHash();
        
        // Shred it! (e.g., turns into "$2a$10$wK9...")
        String encryptedPassword = passwordEncoder.encode(plainPassword);
        
        // Save the shredded version back into the User object
        user.setPasswordHash(encryptedPassword);

        // STEP 3: SAVE TO DATABASE
        // ---------------------------------------------------------------
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}