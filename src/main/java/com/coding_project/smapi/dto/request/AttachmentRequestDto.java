package com.coding_project.smapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload for attaching a file reference to a Task")
public class AttachmentRequestDto {

    @Schema(
            description = "Original name of the uploaded file including its extension",
            example = "floor3-inspection-report.pdf",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fileName;

    @Schema(
            description = "Publicly accessible URL where the file is stored (e.g. an S3 pre-signed URL)",
            example = "https://storage.example.com/attachments/floor3-inspection-report.pdf",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fileUrl;

    @Schema(
            description = "File size in bytes",
            example = "204800"
    )
    private Long fileSize;

    @Schema(
            description = "MIME type of the file",
            example = "application/pdf"
    )
    private String contentType;
}
