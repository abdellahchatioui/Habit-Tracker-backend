package com.example.habittracker.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String frequency; // DAILY, WEEKLY

    private Boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<HabitLog> logs = new ArrayList<>();

    public Habit(String title, String description, String frequency, Boolean completed, User user) {
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.completed = completed;
        this.user = user;
    }

}