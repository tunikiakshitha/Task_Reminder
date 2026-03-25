package com.example.taskremainder.scedular;

import com.example.taskremainder.model.Taskmodel;
import com.example.taskremainder.service.TaskManager;
import com.example.taskremainder.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TaskScedular {

    private final TaskManager service;
    private final EmailService emailService;

    public TaskScedular(TaskManager service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 5000)
    public void checkTasks() {

        System.out.println("Scheduler running...");

        List<Taskmodel> tasks = service.getAllTasks();

        for (Taskmodel task : tasks) {

            if (task.getStatus().equalsIgnoreCase("PENDING") &&
                    task.getDueDate().isBefore(LocalDateTime.now())) {

                System.out.println("⚠️ Task overdue: " + task.getTitle());

                // SEND EMAIL
                emailService.sendEmail(
                        "your-email@gmail.com",
                        "Task Reminder",
                        "Task overdue: " + task.getTitle()
                );
            }
        }
    }
}