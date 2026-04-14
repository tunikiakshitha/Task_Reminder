package com.example.taskremainder.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.time.format.DateTimeFormatter;

import com.example.taskremainder.entity.Task;

@Service
public class CsvService {

    public String convertToCSV(List<Task> tasks) {

        StringBuilder sb = new StringBuilder();

        // ✅ HEADER (only once)
        sb.append("Title,Description,Due Date,Status\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // ✅ EACH TASK IN NEW LINE
        for (Task task : tasks) {

            String title = task.getTitle() != null ? task.getTitle() : "";
            String desc = task.getDescription() != null ? task.getDescription() : "";
            String date = task.getDueDate() != null ? task.getDueDate().format(formatter) : "";
            String status = task.getStatus() != null ? task.getStatus() : "";

            sb.append(title).append(",")
                    .append(desc).append(",")
                    .append(date).append(",")
                    .append(status)
                    .append("\n");  // ✅ VERY IMPORTANT
        }

        return sb.toString();
    }
}