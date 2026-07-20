package com.coding_project.smapi.controller;

import com.coding_project.smapi.dto.request.TaskRequestDto;
import com.coding_project.smapi.dto.response.TaskResponseDto;
import com.coding_project.smapi.enums.Priority;
import com.coding_project.smapi.enums.TaskStatus;
import com.coding_project.smapi.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping()
    public List<TaskResponseDto> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/{id}")
    public TaskResponseDto getTaskById(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @GetMapping("/status/{status}")
    public List<TaskResponseDto> getTasksByStatus(@PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }

    @GetMapping("/priority/{priority}")
    public List<TaskResponseDto> getTasksByPriority(@PathVariable Priority priority) {
        return taskService.getTasksByPriority(priority);
    }

    @GetMapping("/deadline/{days}")
    public List<TaskResponseDto> getTasksByDeadline(@PathVariable int days) {
        return taskService.getTasksByDeadline(days);
    }

    @PostMapping("/add")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto createdTask = taskService.createTask(taskRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto updatedTask = taskService.updateTask(id, taskRequestDto);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
