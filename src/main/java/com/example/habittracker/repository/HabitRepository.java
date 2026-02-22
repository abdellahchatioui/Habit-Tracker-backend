package com.example.habittracker.repository;

import com.example.habittracker.Entity.Habit;
import com.example.habittracker.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByUser(User user);
}