package com.example.taskremainder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import com.example.taskremainder.entity.Task;
import com.example.taskremainder.entity.User;
import com.example.taskremainder.service.TaskService;
import com.example.taskremainder.service.EmailService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;
    private final EmailService emailService;

    public TaskController(TaskService service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }

    // ================= DASHBOARD =================
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) return "redirect:/login";

        List<Task> tasks = service.getTasks(user);

        if (tasks == null) tasks = new ArrayList<>();

        model.addAttribute("tasks", tasks);
        model.addAttribute("total", service.totalTasks(user));
        model.addAttribute("pending", service.pendingTasks(user));
        model.addAttribute("completed", service.completedTasks(user));
        model.addAttribute("overdue", service.overdueTasks(user));

        return "dashboard";
    }

    // ================= ADD TASK =================
    @PostMapping("/add")
    public String addTask(Task task,
                          HttpSession session,
                          RedirectAttributes redirect) {

        User user = (User) session.getAttribute("user");

        if (user == null) return "redirect:/login";

        task.setUser(user);

        service.saveTask(task);

        redirect.addFlashAttribute("success", "Task Added Successfully!");

        return "redirect:/tasks/dashboard";
    }

    // ================= EDIT PAGE =================
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {

        Task task = service.findById(id);
        model.addAttribute("task", task);

        return "edit-task";
    }

    // ================= UPDATE TASK =================
    @PostMapping("/update")
    public String updateTask(Task task,
                             HttpSession session,
                             RedirectAttributes redirect) {

        User user = (User) session.getAttribute("user");

        if (user == null) return "redirect:/login";

        task.setUser(user);

        service.saveTask(task);

        redirect.addFlashAttribute("success", "Task Updated Successfully!");

        return "redirect:/tasks/dashboard";
    }

    // ================= DELETE =================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirect) {

        service.deleteTask(id);

        redirect.addFlashAttribute("success", "Task Deleted Successfully!");

        return "redirect:/tasks/dashboard";
    }

    // ================= FILTER =================
    @GetMapping("/filter")
    public String filter(@RequestParam String status,
                         Model model,
                         HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) return "redirect:/login";

        List<Task> tasks = service.getTasksByStatus(user, status);

        if (tasks == null) tasks = new ArrayList<>();

        model.addAttribute("tasks", tasks);
        model.addAttribute("total", service.totalTasks(user));
        model.addAttribute("pending", service.pendingTasks(user));
        model.addAttribute("completed", service.completedTasks(user));
        model.addAttribute("overdue", service.overdueTasks(user));

        return "dashboard";
    }
}