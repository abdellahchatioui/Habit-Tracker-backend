package com.example.habittracker.controller;

import com.example.habittracker.Entity.*;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/habits")
@CrossOrigin
public class HabitController {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public HabitController(HabitRepository habitRepository, UserRepository userRepository) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    // GET all habits for authenticated user
    @GetMapping
    public List<Habit> getHabits() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // System.out.println("Email : "+ email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return habitRepository.findByUser(user);
    }

    // CREATE habit
    @PostMapping
    public Habit createHabit(@RequestBody Habit habit) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        habit.setUser(user);

        return habitRepository.save(habit);
    }

    // DELETE habit
    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable Long id) {

        habitRepository.deleteById(id);
    }
}