package com.coding_project.smapi.dto.request;

import com.coding_project.smapi.enums.Priority;
import com.coding_project.smapi.enums.TaskStatus;
import com.coding_project.smapi.enums.TaskType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload for creating or updating a Task")
public class TaskRequestDto {

    @Schema(
            description = "Human-readable name of the task",
            example = "Replace faulty circuit breaker on floor 3",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "Detailed description explaining what the task involves",
            example = "Locate and swap the 40 A breaker in panel B-304"
    )
    private String description;

    @Schema(
            description = "Category of the task",
            example = "MAINTENANCE",
            allowableValues = {"FEATURE", "BUG", "TECHNICAL_DEBT", "RESEARCH", "MAINTENANCE", "OTHER"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private TaskType type;

    @Schema(
            description = "Priority level of the task",
            example = "HIGH",
            allowableValues = {"CRITICAL", "HIGH", "MEDIUM", "LOW"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Priority priority;

    @Schema(
            description = "Current lifecycle status of the task",
            example = "CREATED",
            allowableValues = {"CREATED", "IN_PROGRESS", "BLOCKED", "IN_REVIEW", "DONE", "CANCELLED"}
    )
    private TaskStatus status;

    @Schema(
            description = "Date and time when work on this task is scheduled to begin (ISO-8601)",
            example = "2025-07-02T09:00:00"
    )
    private LocalDateTime startDate;

    @Schema(
            description = "Date and time when the task was completed (ISO-8601)",
            example = "2025-07-03T15:30:00"
    )
    private LocalDateTime endDate;

    @Schema(
            description = "Latest date and time by which the task must be finished (ISO-8601)",
            example = "2025-07-05T23:59:59",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDateTime deadline;

    @Schema(
            description = "Identifier of the parent Job this task belongs to",
            example = "42",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long jobId;
}
