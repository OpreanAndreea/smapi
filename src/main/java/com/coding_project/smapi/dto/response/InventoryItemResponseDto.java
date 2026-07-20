package com.coding_project.smapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Inventory item with its current stock level and pricing")
public class InventoryItemResponseDto {

    @Schema(description = "Internal surrogate identifier of the inventory item", example = "7")
    private Long id;

    @Schema(description = "Unique SKU code", example = "CBR-40A-SIEMENS")
    private String code;

    @Schema(description = "Human-readable name of the item", example = "40A Circuit Breaker – Siemens")
    private String name;

    @Schema(description = "Current unit price in USD", example = "24.99")
    private BigDecimal unitPrice;

    @Schema(description = "Current quantity in stock", example = "120")
    private int quantity;
}
