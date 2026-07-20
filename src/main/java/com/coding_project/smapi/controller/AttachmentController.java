package com.coding_project.smapi.controller;

import com.coding_project.smapi.dto.request.AttachmentRequestDto;
import com.coding_project.smapi.dto.response.AttachmentResponseDto;
import com.coding_project.smapi.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/tasks/{id}/add-attachment")
    public ResponseEntity<AttachmentResponseDto> addAttachment(
            @PathVariable Long id,
            @RequestBody AttachmentRequestDto attachmentRequestDto) {
        AttachmentResponseDto addedAttachment = attachmentService.addAttachment(id, attachmentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedAttachment);
    }

    @DeleteMapping("/tasks/{id}/attachment/{uuid}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable UUID uuid) {
        attachmentService.deleteAttachment(uuid);
        return ResponseEntity.noContent().build();
    }
}
