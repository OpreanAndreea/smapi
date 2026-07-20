package com.coding_project.smapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Attachment record linked to a Task")
public class AttachmentResponseDto {

    @Schema(description = "Unique UUID identifier of the attachment", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Original file name including its extension", example = "floor3-inspection-report.pdf")
    private String fileName;

    @Schema(description = "Publicly accessible URL of the stored file",
            example = "https://storage.example.com/attachments/floor3-inspection-report.pdf")
    private String fileUrl;

    @Schema(description = "File size in bytes", example = "204800")
    private Long fileSize;

    @Schema(description = "MIME type of the file", example = "application/pdf")
    private String contentType;

    @Schema(description = "Date and time the attachment was uploaded (ISO-8601)", example = "2025-07-02T10:30:00")
    private LocalDateTime uploadedAt;
}
