package com.coding_project.smapi.dto.response;

import com.coding_project.smapi.enums.Priority;
import com.coding_project.smapi.enums.TaskStatus;
import com.coding_project.smapi.enums.TaskType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Full Task record returned by the API")
public class TaskResponseDto {

    @Schema(description = "Surrogate identifier of the task", example = "101")
    private Long id;

    @Schema(description = "Short name of the task", example = "Replace faulty circuit breaker on floor 3")
    private String name;

    @Schema(description = "Detailed description of the task",
            example = "Locate and swap the 40 A breaker in panel B-304")
    private String description;

    @Schema(description = "Category of the task",
            example = "MAINTENANCE",
            allowableValues = {"FEATURE", "BUG", "TECHNICAL_DEBT", "RESEARCH", "MAINTENANCE", "OTHER"})
    private TaskType type;

    @Schema(description = "Priority of the task",
            example = "HIGH",
            allowableValues = {"CRITICAL", "HIGH", "MEDIUM", "LOW"})
    private Priority priority;

    @Schema(description = "Current lifecycle status",
            example = "IN_PROGRESS",
            allowableValues = {"CREATED", "IN_PROGRESS", "BLOCKED", "IN_REVIEW", "DONE", "CANCELLED"})
    private TaskStatus status;

    @Schema(description = "Timestamp when the task record was created (ISO-8601)", example = "2025-06-30T14:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp of the last update to the task record (ISO-8601)", example = "2025-07-01T09:15:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Scheduled start date/time (ISO-8601)", example = "2025-07-02T09:00:00")
    private LocalDateTime startDate;

    @Schema(description = "Actual completion date/time (ISO-8601)", example = "2025-07-03T15:30:00")
    private LocalDateTime endDate;

    @Schema(description = "Deadline date/time (ISO-8601)", example = "2025-07-05T23:59:59")
    private LocalDateTime deadline;

    @Schema(description = "Identifier of the parent Job", example = "42")
    private Long jobId;

    @Schema(description = "File attachments associated with this task")
    private List<AttachmentResponseDto> attachments;
}
