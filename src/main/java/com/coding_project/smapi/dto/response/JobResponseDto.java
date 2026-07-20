package com.coding_project.smapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime deadline;
    private List<TaskResponseDto> tasks;
    private List<JobMaterialResponseDto> materials;
    private BigDecimal totalMaterialsCost;
    private BigDecimal serviceFee;
    private BigDecimal overallCost;
}
