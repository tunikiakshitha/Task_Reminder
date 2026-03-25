package com.example.taskremainder.service;

import com.example.taskremainder.model.Taskmodel;
import com.example.taskremainder.Repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskManager {

    private final TaskRepository repository;

    // Constructor Injection
    public TaskManager(TaskRepository repository){
        this.repository = repository;
    }

    // ADD TASK
    public void addTask(Taskmodel task){
        repository.addTask(task);
    }

    // GET ALL TASKS
    public List<Taskmodel> getAllTasks(){
        return repository.getTasks();
    }

    // DELETE TASK
    public void deleteTask(Long id){
        repository.deleteTask(id);
    }

    // UPDATE TASK
    public void updateTask(Long id, Taskmodel task){
        repository.updateTask(id, task);
    }
}