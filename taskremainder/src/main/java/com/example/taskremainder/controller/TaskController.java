package com.example.taskremainder.controller;

import com.example.taskremainder.model.Taskmodel;
import com.example.taskremainder.service.TaskManager;
import org.springframework.web.bind.annotation.*;
import com.example.taskremainder.service.EmailService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskManager service;
    private final EmailService emailService;

    public TaskController(TaskManager service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }

    // GET ALL TASKS
    @GetMapping("/all")
    public List<Taskmodel> getAllTasks() {
        return service.getAllTasks();
    }

    // ADD TASK
    @PostMapping("/add")
    public String addTask(@RequestBody Taskmodel task) {
        service.addTask(task);
        return "Task added successfully";
    }

    // UPDATE TASK
    @PutMapping("/update/{id}")
    public String updateTask(@PathVariable Long id,
                             @RequestBody Taskmodel task) {

        service.updateTask(id, task);
        return "Task updated successfully";
    }

    // DELETE TASK
    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {

        service.deleteTask(id);
        return "Task deleted successfully";
    }
    @GetMapping("/send-mail")
    public String sendMail() {
        emailService.sendEmail(
                "tunikiakshitha55@gmail.com",
                "Test Mail",
                "Hello! Email is working 🎉"
        );
        return "Email Sent!";
    }
}