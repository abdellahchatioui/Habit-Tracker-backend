package com.example.habittracker.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    @Column(nullable = false)
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotBlank(message = "Frequency is required")
    @Pattern(regexp = "^(DAILY|WEEKLY)$", message = "Frequency must be either DAILY or WEEKLY")
    @Column(nullable = false)
    private String frequency;

    @NotNull(message = "Completed status cannot be null")
    private Boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
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