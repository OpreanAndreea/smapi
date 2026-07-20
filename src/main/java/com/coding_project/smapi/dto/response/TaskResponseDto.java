package com.coding_project.smapi.dto.response;

import com.coding_project.smapi.enums.Priority;
import com.coding_project.smapi.enums.TaskStatus;
import com.coding_project.smapi.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {
    private Long id;
    private String name;
    private String description;
    private TaskType type;
    private Priority priority;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime deadline;
    private Long jobId;
    private List<AttachmentResponseDto> attachments;
}
