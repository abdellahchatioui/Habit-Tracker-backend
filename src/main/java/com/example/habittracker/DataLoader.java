package com.example.habittracker;

import com.example.habittracker.Entity.User;
import com.example.habittracker.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void loadData() {

        if (userRepository.count() == 0) {

            User user = new User();
            user.setName("Test User");
            user.setEmail("test@test.com");
            user.setPassword("1234");
            user.setRole("USER");
            user.setEnabled(true);

            userRepository.save(user);

            System.out.println("Test user inserted!");
        }
    }
}