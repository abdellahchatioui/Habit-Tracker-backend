package com.example.habittracker.controller;

import com.example.habittracker.Entity.User;
import com.example.habittracker.dto.LoginRequest;
import com.example.habittracker.repository.UserRepository;
import com.example.habittracker.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public UserRepository userRepository;

    @GetMapping
    public String test(){
        return "server work !!!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newUser) {

        Optional<User> existingUser = userRepository.findByEmail(newUser.getEmail());

        if (existingUser.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Email already exists"));
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRole("USER");
        newUser.setEnabled(true);

        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }


}
