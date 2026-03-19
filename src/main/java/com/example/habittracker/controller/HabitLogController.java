package com.example.habittracker.controller;

import com.example.habittracker.Entity.HabitLog;
import com.example.habittracker.service.HabitLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/habits")
@CrossOrigin
@Controller
public class HabitLogController {

    private final HabitLogService habitLogService;

    public HabitLogController(HabitLogService habitLogService) {
        this.habitLogService = habitLogService;
    }

    @GetMapping("/{id}/logs")
    public ResponseEntity<?> getLogsByMonth(
            @PathVariable Long id,
            @RequestParam int year,
            @RequestParam int month,
            Authentication authentication
    ) {

        List<HabitLog> logs =
                habitLogService.getLogsForMonth(id, year, month, authentication.getName());

        return ResponseEntity.ok(logs);
    }

    @PostMapping("/{id}/log")
    public ResponseEntity<?> logHabit(
            @PathVariable Long id,
            @RequestParam String date,
            Authentication authentication
    ) {
        HabitLog log = habitLogService.trackHabit(
                id,
                LocalDate.parse(date),
                authentication.getName()
        );

        return ResponseEntity.ok(log);
    }

    @DeleteMapping("/{habitId}/log")
    public ResponseEntity<?> deleteLog(
            @PathVariable Long habitId,
            @RequestParam String date
    ) {
        habitLogService.deleteLog(habitId, date);
        return ResponseEntity.ok().build();
    }

}
