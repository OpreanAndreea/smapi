package com.coding_project.smapi.controller;

import com.coding_project.smapi.dto.request.InventoryItemRequestDto;
import com.coding_project.smapi.dto.response.InventoryItemResponseDto;
import com.coding_project.smapi.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<InventoryItemResponseDto> getAllInventory() {
        return inventoryService.getAllItems();
    }

    @PostMapping("/restock")
    public ResponseEntity<List<InventoryItemResponseDto>> restockItems(
            @RequestBody List<InventoryItemRequestDto> restockItems) {
        List<InventoryItemResponseDto> updatedItems = inventoryService.restockItems(restockItems);
        return ResponseEntity.ok(updatedItems);
    }
}