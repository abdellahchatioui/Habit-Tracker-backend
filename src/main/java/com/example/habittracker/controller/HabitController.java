package com.example.habittracker.controller;

import com.example.habittracker.Entity.*;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.repository.UserRepository;
import com.example.habittracker.service.HabitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/habits")
@CrossOrigin
@Controller
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }


    @GetMapping
    public ResponseEntity<?> getHabits() {
        List<Habit> allHabits = habitService.getHabits();
        return ResponseEntity.ok(allHabits);
    }


    @PostMapping()
    public ResponseEntity<?> createHabit(@RequestBody Habit habit) {
        Habit savedHabit = habitService.saveHabit(habit);
        return ResponseEntity.ok(savedHabit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHabit(
            @PathVariable Long id,
            @RequestBody Habit updatedHabit,
            Authentication authentication
    ) {

        Habit habit = habitService.updateHabit(id, updatedHabit, authentication.getName());

        return ResponseEntity.ok(habit);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHabit(@PathVariable Long id) {
        habitService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}