package com.example.habittracker.controller;

import com.example.habittracker.Entity.User;
import com.example.habittracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    public UserRepository userRepository;


    @GetMapping
    public String test(){
        return "good !!!";
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody User loginRequest){

        Optional<User> user = userRepository.findByEmailAndPassword(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        Map<String,String> response = new HashMap<>();

        if (user.isPresent()) {
            response.put("token", "test-token-123");
        } else {
            response.put("error", "Invalid credentials");
        }
        return response;
    }



}
