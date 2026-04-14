package com.example.taskremainder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.example.taskremainder.entity.Task;
import com.example.taskremainder.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    int countByUser(User user);

    int countByUserAndStatus(User user, String status);
}