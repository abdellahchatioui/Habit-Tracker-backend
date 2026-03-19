package com.example.habittracker.service;

import com.example.habittracker.Entity.Habit;
import com.example.habittracker.Entity.HabitLog;
import com.example.habittracker.repository.HabitLogRepository;
import com.example.habittracker.repository.HabitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HabitLogService {

    private final HabitLogRepository habitLogRepository;
    private final HabitRepository habitRepository;

    public HabitLogService(HabitLogRepository habitLogRepository, HabitRepository habitRepository) {
        this.habitLogRepository = habitLogRepository;
        this.habitRepository = habitRepository;
    }

    public HabitLog trackHabit(Long habitId, LocalDate date, String userEmail) {

        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        if (!habit.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized");
        }

        Optional<HabitLog> existingLog =
                habitLogRepository.findByHabitIdAndDate(habitId, date);

        if (existingLog.isPresent()) {

            // Toggle completion
            HabitLog log = existingLog.get();
            log.setCompleted(!log.getCompleted());
            return habitLogRepository.save(log);

        } else {

            HabitLog log = new HabitLog();
            log.setHabit(habit);
            log.setDate(date);
            log.setCompleted(true);

            return habitLogRepository.save(log);
        }
    }

    public List<HabitLog> getLogsForMonth(
            Long habitId,
            int year,
            int month,
            String userEmail
    ) {

        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        if (!habit.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized");
        }

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return habitLogRepository
                .findByHabitIdAndDateBetween(habitId, start, end);
    }

    @Transactional
    public void deleteLog(Long habitId, String dateStr) {

        LocalDate date = LocalDate.parse(dateStr);

        habitLogRepository.deleteByHabitIdAndDate(habitId, date);
    }

}
