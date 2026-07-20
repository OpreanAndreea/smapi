package com.coding_project.smapi.service;

import com.coding_project.smapi.dto.request.AttachmentRequestDto;
import com.coding_project.smapi.dto.response.AttachmentResponseDto;
import com.coding_project.smapi.mapper.AttachmentMapper;
import com.coding_project.smapi.entity.Attachment;
import com.coding_project.smapi.entity.Task;
import com.coding_project.smapi.repository.AttachmentRepository;
import com.coding_project.smapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AttachmentMapper attachmentMapper;

    public AttachmentResponseDto addAttachment(Long taskId, AttachmentRequestDto attachmentRequestDto) {
        Attachment att = new Attachment();

        att.setContentType(attachmentRequestDto.getContentType());
        att.setFileName(attachmentRequestDto.getFileName());
        att.setFileSize(attachmentRequestDto.getFileSize());
        att.setFileUrl(attachmentRequestDto.getFileUrl());

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent Job not found"));
        att.setTask(task);

        return attachmentMapper.toDto(attachmentRepository.save(att));
    }

    public void deleteAttachment(UUID id) {
        if (!attachmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id: " + id);
        }
        attachmentRepository.deleteById(id);
    }

}
