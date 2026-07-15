package com.coding_project.smapi.service;

import com.coding_project.smapi.dto.TaskRequestDto;
import com.coding_project.smapi.dto.TaskResponseDto;
import com.coding_project.smapi.enums.Priority;
import com.coding_project.smapi.enums.TaskStatus;
import com.coding_project.smapi.enums.TaskType;
import com.coding_project.smapi.mapper.TaskMapper;
import com.coding_project.smapi.model.Job;
import com.coding_project.smapi.model.Task;
import com.coding_project.smapi.repository.JobRepository;
import com.coding_project.smapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private TaskMapper taskMapper;

    public List<TaskResponseDto> getTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .toList();
    }

    public TaskResponseDto getTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return taskMapper.toDto(task);
    }

    public List<TaskResponseDto> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status).stream()
                .map(task -> taskMapper.toDto(task))
                .toList();
    }

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        Task task = new Task();

        task.setName(taskRequestDto.getName());
        task.setDescription(taskRequestDto.getDescription());
        task.setType(Objects.requireNonNullElse(taskRequestDto.getType(), TaskType.OTHER));
        task.setPriority(Objects.requireNonNullElse(taskRequestDto.getPriority(), Priority.LOW));
        task.setStatus(Objects.requireNonNullElse(taskRequestDto.getStatus(), TaskStatus.CREATED));
        task.setDeadline(taskRequestDto.getDeadline());

        Job job = jobRepository.findById(taskRequestDto.getJobId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent Job not found"));
        task.setJob(job);

        return taskMapper.toDto(taskRepository.save(task));
    }

    public TaskResponseDto updateTask(Long id, TaskRequestDto taskRequestDto) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id: " + id));

        if (taskRequestDto.getName() != null) {
            existingTask.setName(taskRequestDto.getName());
        }

        if (taskRequestDto.getDescription() != null) {
            existingTask.setDescription(taskRequestDto.getDescription());
        }

        if (taskRequestDto.getType() != null) {
            existingTask.setType(taskRequestDto.getType());
        }

        if (taskRequestDto.getPriority() != null) {
            existingTask.setPriority(taskRequestDto.getPriority());
        }

        if (taskRequestDto.getStatus() != null) {
            existingTask.setStatus(taskRequestDto.getStatus());
        }

        if (taskRequestDto.getDeadline() != null) {
            existingTask.setDeadline(taskRequestDto.getDeadline());
        }

        if (taskRequestDto.getJobId() != null) {
            Job job = jobRepository.findById(taskRequestDto.getJobId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found with id: " + taskRequestDto.getJobId()));
            existingTask.setJob(job);
        }

        return taskMapper.toDto(taskRepository.save(existingTask));
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
