package com.coding_project.smapi.mapper;

import com.coding_project.smapi.dto.JobResponseDto;
import com.coding_project.smapi.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    @Autowired
    private TaskMapper taskMapper;

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
        return dto;
    }
}