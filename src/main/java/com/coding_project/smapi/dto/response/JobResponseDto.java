package com.coding_project.smapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Full Job record returned by the API, including nested tasks, materials, and cost summary")
public class JobResponseDto {

    @Schema(description = "Surrogate identifier of the job", example = "42")
    private Long id;

    @Schema(description = "Short title of the job", example = "Office Electrical Rewiring")
    private String title;

    @Schema(description = "Detailed description of the work scope",
            example = "Replace all wiring on floors 2–4 and install updated circuit breakers")
    private String description;

    @Schema(description = "Scheduled start date/time (ISO-8601)", example = "2025-07-01T08:00:00")
    private LocalDateTime startDate;

    @Schema(description = "Actual completion date/time (ISO-8601)", example = "2025-07-10T17:00:00")
    private LocalDateTime endDate;

    @Schema(description = "Deadline date/time (ISO-8601)", example = "2025-07-15T23:59:59")
    private LocalDateTime deadline;

    @Schema(description = "Ordered list of tasks associated with this job")
    private List<TaskResponseDto> tasks;

    @Schema(description = "Inventory materials allocated to this job")
    private List<JobMaterialResponseDto> materials;

    @Schema(description = "Sum of all material line totals (USD)", example = "249.90")
    private BigDecimal totalMaterialsCost;

    @Schema(description = "Service / labour fee applied to the job (USD)", example = "500.00")
    private BigDecimal serviceFee;

    @Schema(description = "Grand total cost (materials + service fee) in USD", example = "749.90")
    private BigDecimal overallCost;
}
