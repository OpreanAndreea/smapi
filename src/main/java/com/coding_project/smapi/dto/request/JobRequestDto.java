package com.coding_project.smapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload for creating or updating a Job")
public class JobRequestDto {

    @Schema(
            description = "Short, descriptive title of the job",
            example = "Office Electrical Rewiring",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @Schema(
            description = "Detailed description of the work to be performed",
            example = "Replace all wiring on floors 2–4 and install updated circuit breakers"
    )
    private String description;

    @Schema(
            description = "Date and time when work on this job is scheduled to begin (ISO-8601)",
            example = "2025-07-01T08:00:00"
    )
    private LocalDateTime startDate;

    @Schema(
            description = "Date and time when work on this job was completed (ISO-8601)",
            example = "2025-07-10T17:00:00"
    )
    private LocalDateTime endDate;

    @Schema(
            description = "Latest date and time by which the job must be completed (ISO-8601)",
            example = "2025-07-15T23:59:59",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDateTime deadline;
}
