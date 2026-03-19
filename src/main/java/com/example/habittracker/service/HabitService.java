package com.example.habittracker.service;

import com.example.habittracker.Entity.Habit;
import com.example.habittracker.Entity.User;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public HabitService(HabitRepository habitRepository, UserRepository userRepository) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }


    public List<Habit> getHabits() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return habitRepository.findByUser(user);
    }

    public Habit saveHabit(Habit habit) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        habit.setUser(user);

        return habitRepository.save(habit);
    }

    public Habit updateHabit(Long id, Habit updatedHabit, String userEmail) {

        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        // Security check
        if (!habit.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized");
        }

        habit.setTitle(updatedHabit.getTitle());
        habit.setDescription(updatedHabit.getDescription());
        habit.setFrequency(updatedHabit.getFrequency());
        habit.setCompleted(updatedHabit.getCompleted());

        return habitRepository.save(habit);
    }

    public void deleteById(Long id) {
        habitRepository.deleteById(id);
    }
}
