package com.example.taskremainder.controller;

import com.example.taskremainder.entity.User;
import com.example.taskremainder.service.UserService;
import com.example.taskremainder.service.EmailService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    private final UserService userService;
    private final EmailService emailService;

    public PageController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    // ================= PAGES =================
    @GetMapping("/")
    public String home() { return "login"; }

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @GetMapping("/register")
    public String registerPage() { return "registration"; }

    @GetMapping("/forgot")
    public String forgotPage() { return "forgot"; }

    // ================= REGISTER =================
    @PostMapping("/register")
    public String register(User user, HttpSession session, Model model) {

        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists");
            return "registration";
        }

        String otp = String.valueOf((int)(Math.random() * 9000) + 1000);

        session.setAttribute("otp", otp);
        session.setAttribute("tempUser", user);

        System.out.println("Generated OTP: " + otp);

        try {
            emailService.sendEmail(user.getEmail(),
                    "OTP Verification",
                    "Your OTP: " + otp);
        } catch (Exception e) {
            System.out.println("Email failed: " + e.getMessage());
        }

        return "otp";
    }

    // ================= VERIFY REGISTER =================
    @PostMapping("/verifyRegister")
    public String verifyRegister(@RequestParam String otp, HttpSession session) {

        String sessionOtp = (String) session.getAttribute("otp");

        if (sessionOtp != null && sessionOtp.equals(otp)) {

            User user = (User) session.getAttribute("tempUser");
            userService.save(user);

            return "redirect:/login";   // ✅ correct
        }

        return "otp";
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User user = userService.login(email, password);

        if (user != null) {
            session.setAttribute("user", user);   // ✅ FIXED KEY
            return "redirect:/tasks/dashboard";   // ✅ redirect works now
        }

        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    // ================= FORGOT PASSWORD =================
    @PostMapping("/forgot")
    public String forgot(@RequestParam String email, HttpSession session) {

        String otp = String.valueOf((int)(Math.random() * 9000) + 1000);

        session.setAttribute("otp", otp);
        session.setAttribute("email", email);

        try {
            emailService.sendEmail(email,
                    "Reset OTP",
                    "Your OTP: " + otp);
        } catch (Exception e) {
            System.out.println("Email failed: " + e.getMessage());
        }

        return "verify";
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String otp, HttpSession session) {

        String sessionOtp = (String) session.getAttribute("otp");

        if (sessionOtp != null && sessionOtp.equals(otp)) {
            return "resetpassword";
        }

        return "verify";
    }

    @PostMapping("/reset")
    public String reset(@RequestParam String password, HttpSession session) {

        String email = (String) session.getAttribute("email");

        userService.updatePassword(email, password);

        return "redirect:/login";
    }

    // ================= LOGOUT =================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}