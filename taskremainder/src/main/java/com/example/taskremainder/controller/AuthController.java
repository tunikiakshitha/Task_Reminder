package com.example.taskremainder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpSession;

import com.example.taskremainder.entity.User;
import com.example.taskremainder.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    //@PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session) {

        User user = userService.login(email, password);

        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/dashboard";
        }

        return "login";
    }
}