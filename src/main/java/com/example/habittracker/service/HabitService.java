package com.example.habittracker.service;

import com.example.habittracker.Entity.Habit;
import com.example.habittracker.repository.HabitRepository;
import org.springframework.stereotype.Service;

@Service
public class HabitService {


    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
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
}
