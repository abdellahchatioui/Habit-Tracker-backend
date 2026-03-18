package com.example.habittracker.repository;

import com.example.habittracker.Entity.HabitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {

    Optional<HabitLog> findByHabitIdAndDate(Long habitId, LocalDate date);

    List<HabitLog> findByHabitIdAndDateBetween(
            Long habitId,
            LocalDate start,
            LocalDate end
    );

    void deleteByHabitIdAndDate(Long habitId, LocalDate date);
}