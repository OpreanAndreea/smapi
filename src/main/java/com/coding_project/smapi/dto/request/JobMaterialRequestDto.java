package com.coding_project.smapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload for allocating a single inventory item to a Job")
public class JobMaterialRequestDto {

    @Schema(
            description = "Unique SKU code of the inventory item to allocate",
            example = "CBR-40A-SIEMENS",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String inventoryItemCode;

    @Schema(
            description = "Number of units to allocate from inventory to this job",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int quantity;
}
