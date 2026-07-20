package com.coding_project.smapi.dto.request;

import com.coding_project.smapi.enums.Priority;
import com.coding_project.smapi.enums.TaskStatus;
import com.coding_project.smapi.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {
    private String name;
    private String description;
    private TaskType type;
    private Priority priority;
    private TaskStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime deadline;
    private Long jobId;
}
