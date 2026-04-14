package com.example.taskremainder.service;

import com.example.taskremainder.entity.User;
import com.example.taskremainder.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // SAVE USER
    public void save(User user) {
        userRepository.save(user);
    }

    // LOGIN
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // 🔥 THIS IS THE MISSING METHOD (IMPORTANT)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // UPDATE PASSWORD
    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }
}