package com.example.taskremainder.scedular;

import com.example.taskremainder.entity.Task;
import com.example.taskremainder.service.TaskService;
import com.example.taskremainder.service.EmailService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CustomTaskSchedular {

    private final TaskService service;
    private final EmailService emailService;

    public CustomTaskSchedular(TaskService service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 60000) // every 1 minute
    public void checkTasks() {

        System.out.println("Scheduler Running...");

        List<Task> tasks = service.getAllTasks();

        for (Task task : tasks) {

            // ✅ Skip if user not present
            if (task.getUser() == null) continue;

            // ✅ Skip if dueDate is null
            if (task.getDueDate() == null) continue;

            // ✅ Skip if status is null
            if (task.getStatus() == null) continue;

            // ✅ Check overdue
            if (task.getStatus().equalsIgnoreCase("PENDING") &&
                    task.getDueDate().isBefore(LocalDateTime.now())) {

                System.out.println("Overdue Task: " + task.getTitle());

                try {
                    emailService.sendEmail(
                            task.getUser().getEmail(),
                            "Task Reminder",
                            "Your task \"" + task.getTitle() + "\" is overdue!"
                    );
                } catch (Exception e) {
                    System.out.println("Email failed: " + e.getMessage());
                }
            }
        }
    }
}