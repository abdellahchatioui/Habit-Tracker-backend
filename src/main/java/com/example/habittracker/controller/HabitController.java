package com.example.habittracker.controller;

import com.example.habittracker.Entity.*;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.repository.UserRepository;
import com.example.habittracker.service.HabitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/habits")
@CrossOrigin
public class HabitController {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;
    private final HabitService habitService;

    public HabitController(HabitRepository habitRepository, UserRepository userRepository, HabitService habitService) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
        this.habitService = habitService;
    }


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


    @PutMapping("/{id}")
    public ResponseEntity<?> updateHabit(
            @PathVariable Long id,
            @RequestBody Habit updatedHabit,
            Authentication authentication
    ) {

        Habit habit = habitService.updateHabit(id, updatedHabit, authentication.getName());

        return ResponseEntity.ok(habit);
    }

    @PostMapping()
    public Habit createHabit(@RequestBody Habit habit) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        habit.setUser(user);

        return habitRepository.save(habit);
    }


    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable Long id) {

        habitRepository.deleteById(id);
    }

    @PostMapping("/{id}/log")
    public ResponseEntity<?> logHabit(
            @PathVariable Long id,
            @RequestParam String date,
            Authentication authentication
    ) {
        HabitLog log = habitService.trackHabit(
                id,
                LocalDate.parse(date),
                authentication.getName()
        );

        return ResponseEntity.ok(log);
    }

    @GetMapping("/{id}/logs")
    public ResponseEntity<?> getLogsByMonth(
            @PathVariable Long id,
            @RequestParam int year,
            @RequestParam int month,
            Authentication authentication
    ) {

        List<HabitLog> logs =
                habitService.getLogsForMonth(id, year, month, authentication.getName());

        return ResponseEntity.ok(logs);
    }
}