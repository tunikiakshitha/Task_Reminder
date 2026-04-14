package com.example.taskremainder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpSession;
import java.util.List;

import com.example.taskremainder.entity.User;
import com.example.taskremainder.entity.Task;
import com.example.taskremainder.service.TaskService;
import com.example.taskremainder.service.CsvService;

@Controller
public class CSVController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CsvService csvService;

    @GetMapping("/export")
    @ResponseBody
    public String export(HttpSession session) {

        User user = (User) session.getAttribute("user"); // ✅ FIX

        if (user == null) return "No Data";

        List<Task> tasks = taskService.getTasks(user);

        return csvService.convertToCSV(tasks);
    }
}