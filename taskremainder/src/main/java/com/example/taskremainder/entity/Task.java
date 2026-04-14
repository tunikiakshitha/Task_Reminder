package com.example.taskremainder.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // ✅ AUTO ID
    private Long id;

    private String title;
    private String description;
    private LocalDateTime dueDate;
    private String status;

    // ================= RELATION =================
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // ================= CONSTRUCTORS =================
    public Task() {}

    public Task(Long id, String title, String description,
                LocalDateTime dueDate, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    // ================= GETTERS & SETTERS =================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {   // optional now
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {   // ✅ NEW
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ================= USER RELATION =================

    public User getUser() {
        return user;
    }

    public void setUser(User user) {   // ✅ THIS FIXES YOUR ERROR
        this.user = user;
    }
}