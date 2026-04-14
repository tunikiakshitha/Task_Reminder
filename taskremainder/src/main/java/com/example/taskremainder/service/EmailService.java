package com.example.taskremainder.service;

import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            System.out.println("Sending mail...");
            mailSender.send(message);


            System.out.println("✅ Email sent successfully to " + to);


        } catch (Exception e) {
            System.out.println("❌ Email failed: " + e.getMessage());
        }
    }
}