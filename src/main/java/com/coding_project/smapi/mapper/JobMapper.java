package com.coding_project.smapi.mapper;

import com.coding_project.smapi.dto.response.JobMaterialResponseDto;
import com.coding_project.smapi.dto.response.JobResponseDto;
import com.coding_project.smapi.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class JobMapper {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private JobMaterialMapper jobMaterialMapper;

    public JobResponseDto toDto(Job job) {
        if (job == null) return null;

        JobResponseDto dto = new JobResponseDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setStartDate(job.getStartDate());
        dto.setEndDate(job.getEndDate());
        dto.setDeadline(job.getDeadline());

        if (job.getTasks() != null) {
            dto.setTasks(job.getTasks().stream()
                    .map(task -> taskMapper.toDto(task, false))
                    .toList());
        }

        if (job.getMaterials() != null) {
            List<JobMaterialResponseDto> materials = job.getMaterials().stream()
                    .map(jobMaterialMapper::toDto)
                    .toList();
            dto.setMaterials(materials);

            BigDecimal totalMaterialsCost = materials.stream()
                    .map(JobMaterialResponseDto::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setTotalMaterialsCost(totalMaterialsCost);

            BigDecimal serviceFee = totalMaterialsCost.multiply(BigDecimal.valueOf(0.50));
            dto.setServiceFee(serviceFee);

            dto.setOverallCost(totalMaterialsCost.add(serviceFee));
        }

        return dto;
    }
}