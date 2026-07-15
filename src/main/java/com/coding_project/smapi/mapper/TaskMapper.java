package com.coding_project.smapi.mapper;

import com.coding_project.smapi.dto.TaskResponseDto;
import com.coding_project.smapi.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    @Autowired
    private AttachmentMapper attachmentMapper;

    public TaskResponseDto toDto(Task task) {
        return toDto(task, true);
    }

    public TaskResponseDto toDto(Task task, boolean includeJobId) {
        if (task == null) return null;

        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setType(task.getType());
        dto.setPriority(task.getPriority());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setDeadline(task.getDeadline());

        if (includeJobId) {
            dto.setJobId(task.getJob() != null ? task.getJob().getId() : null);
        } else {
            dto.setJobId(null);
        }

        if (task.getAttachments() != null) {
            dto.setAttachments(task.getAttachments().stream()
                    .map(attachmentMapper::toDto)
                    .toList());
        }
        return dto;
    }
}