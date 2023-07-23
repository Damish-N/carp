package com.carp.carpentry.controller;

import Dto.UserRequestDto;
import com.carp.carpentry.entity.User;
import com.carp.carpentry.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequestDto userRequestDto) {
        // Validate the request data (optional)
        if (userRequestDto == null || StringUtils.isEmpty(userRequestDto.getUsername()) || StringUtils.isEmpty(userRequestDto.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid user data");
        }

        // Check if the user already exists (optional)
        if (userRepository.findByUsername(userRequestDto.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        // Create the user entity and save it to the database
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        // Set other user details as needed

        userRepository.save(user);

        return ResponseEntity.ok("User created successfully");
    }
}
