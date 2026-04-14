package com.example.taskremainder.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.time.LocalDateTime; // ✅ FIX

import com.example.taskremainder.entity.Task;
import com.example.taskremainder.entity.User;
import com.example.taskremainder.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // ================= SAVE =================
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    // ================= GET USER TASKS =================
    public List<Task> getTasks(User user) {
        return taskRepository.findByUser(user);
    }

    // ================= DELETE =================
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // ================= COUNT =================
    public int totalTasks(User user) {
        return taskRepository.countByUser(user);
    }

    public int pendingTasks(User user) {
        return taskRepository.countByUserAndStatus(user, "PENDING");
    }

    public int completedTasks(User user) {
        return taskRepository.countByUserAndStatus(user, "COMPLETED");
    }

    // ================= FILTER =================
    public List<Task> getTasksByStatus(User user, String status) {
        return taskRepository.findByUser(user)
                .stream()
                .filter(t -> t.getStatus().equalsIgnoreCase(status))
                .toList();
    }

    // ================= GET ALL =================
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // ================= FIND BY ID =================
    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // ================= OVERDUE =================
    public int overdueTasks(User user) {
        return (int) taskRepository.findByUser(user)
                .stream()
                .filter(t ->
                        t.getDueDate() != null &&  // ✅ safety
                                t.getDueDate().isBefore(LocalDateTime.now()) &&
                                t.getStatus().equalsIgnoreCase("PENDING")
                )
                .count();
    }
}