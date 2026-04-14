package com.example.taskremainder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.taskremainder.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email); // ✅ correct
}