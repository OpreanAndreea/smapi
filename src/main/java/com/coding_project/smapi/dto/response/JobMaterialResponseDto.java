package com.coding_project.smapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Material allocation record linking a Job to an inventory item")
public class JobMaterialResponseDto {

    @Schema(description = "Internal surrogate identifier of this allocation record", example = "15")
    private Long id;

    @Schema(description = "Identifier of the parent Job", example = "42")
    private Long jobId;

    @Schema(description = "SKU code of the allocated inventory item", example = "CBR-40A-SIEMENS")
    private String inventoryItemCode;

    @Schema(description = "Number of units allocated to the job", example = "5")
    private int quantity;

    @Schema(description = "Unit price of the item at the time of allocation (USD)", example = "24.99")
    private BigDecimal pricePerItem;

    @Schema(description = "Total cost for this allocation line (quantity × unitPrice) in USD", example = "124.95")
    private BigDecimal totalPrice;
}
