package com.coding_project.smapi.repository;

import com.coding_project.smapi.enums.Priority;
import com.coding_project.smapi.enums.TaskStatus;
import com.coding_project.smapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(Priority priority);

    List<Task> findByDeadlineBetween(LocalDateTime start, LocalDateTime end);
}
