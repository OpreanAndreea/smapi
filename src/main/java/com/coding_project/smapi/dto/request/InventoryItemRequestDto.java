package com.coding_project.smapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload for restocking an inventory item (updates quantity and price)")
public class InventoryItemRequestDto {

    @Schema(
            description = "Unique stock-keeping unit code for the inventory item",
            example = "CBR-40A-SIEMENS",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String code;

    @Schema(
            description = "Human-readable name of the inventory item",
            example = "40A Circuit Breaker – Siemens"
    )
    private String name;

    @Schema(
            description = "Unit price of the item in the base currency (USD)",
            example = "24.99"
    )
    private BigDecimal price;

    @Schema(
            description = "Number of units to add to the existing stock",
            example = "50",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int quantity;
}
