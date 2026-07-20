package com.coding_project.smapi.mapper;

import com.coding_project.smapi.dto.response.AttachmentResponseDto;
import com.coding_project.smapi.entity.Attachment;
import org.springframework.stereotype.Component;

@Component
public class AttachmentMapper {

    public AttachmentResponseDto toDto(Attachment attachment) {
        if (attachment == null) return null;

        AttachmentResponseDto dto = new AttachmentResponseDto();
        dto.setId(attachment.getId());
        dto.setFileName(attachment.getFileName());
        dto.setFileUrl(attachment.getFileUrl());
        dto.setFileSize(attachment.getFileSize());
        dto.setContentType(attachment.getContentType());
        dto.setUploadedAt(attachment.getUploadedAt());
        return dto;
    }
}