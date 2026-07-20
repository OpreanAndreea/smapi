package com.coding_project.smapi.controller;

import com.coding_project.smapi.dto.request.InventoryItemRequestDto;
import com.coding_project.smapi.dto.response.InventoryItemResponseDto;
import com.coding_project.smapi.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory", description = "Read and restock inventory items used across Jobs")
@SecurityRequirement(name = "bearerAuth")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // -------------------------------------------------------------------------
    // GET /inventory
    // -------------------------------------------------------------------------
    @Operation(
            summary = "List all inventory items",
            description = "Returns the complete inventory catalogue with current stock levels and unit prices."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventory retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = InventoryItemResponseDto.class)))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @GetMapping
    public List<InventoryItemResponseDto> getAllInventory() {
        return inventoryService.getAllItems();
    }

    // -------------------------------------------------------------------------
    // POST /inventory/restock
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Restock inventory items",
            description = "Accepts a batch of inventory item codes with quantities to add. " +
                          "Updates existing items' stock; creates new items if the SKU does not yet exist."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Items restocked — updated inventory records returned",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = InventoryItemResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @PostMapping("/restock")
    public ResponseEntity<List<InventoryItemResponseDto>> restockItems(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List of inventory items with quantities to restock", required = true,
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = InventoryItemRequestDto.class))))
            @RequestBody List<InventoryItemRequestDto> restockItems) {
        List<InventoryItemResponseDto> updatedItems = inventoryService.restockItems(restockItems);
        return ResponseEntity.ok(updatedItems);
    }
}
