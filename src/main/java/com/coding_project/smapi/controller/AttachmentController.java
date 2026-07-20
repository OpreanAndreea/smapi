package com.coding_project.smapi.controller;

import com.coding_project.smapi.dto.request.AttachmentRequestDto;
import com.coding_project.smapi.dto.response.AttachmentResponseDto;
import com.coding_project.smapi.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Attachments", description = "Add and remove file attachments on Tasks")
@SecurityRequirement(name = "bearerAuth")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    // -------------------------------------------------------------------------
    // POST /tasks/{id}/add-attachment
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Add an attachment to a Task",
            description = "Associates an external file reference (URL, size, MIME type) with the specified Task."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Attachment added successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AttachmentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Parent Task not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @PostMapping("/tasks/{id}/add-attachment")
    public ResponseEntity<AttachmentResponseDto> addAttachment(
            @Parameter(description = "Surrogate ID of the Task", example = "101", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Attachment metadata payload", required = true,
                    content = @Content(schema = @Schema(implementation = AttachmentRequestDto.class)))
            @RequestBody AttachmentRequestDto attachmentRequestDto) {
        AttachmentResponseDto addedAttachment = attachmentService.addAttachment(id, attachmentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedAttachment);
    }

    // -------------------------------------------------------------------------
    // DELETE /tasks/{id}/attachment/{uuid}
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Remove an attachment from a Task",
            description = "Permanently deletes the attachment identified by its UUID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Attachment deleted — no body returned", content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Attachment not found for the given UUID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @DeleteMapping("/tasks/{id}/attachment/{uuid}")
    public ResponseEntity<Void> deleteAttachment(
            @Parameter(description = "Surrogate ID of the parent Task", example = "101", required = true)
            @PathVariable Long id,
            @Parameter(description = "UUID of the attachment to delete",
                    example = "550e8400-e29b-41d4-a716-446655440000", required = true)
            @PathVariable UUID uuid) {
        attachmentService.deleteAttachment(uuid);
        return ResponseEntity.noContent().build();
    }
}
