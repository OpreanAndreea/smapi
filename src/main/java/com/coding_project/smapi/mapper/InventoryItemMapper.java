package com.coding_project.smapi.mapper;

import com.coding_project.smapi.dto.response.InventoryItemResponseDto;
import com.coding_project.smapi.entity.InventoryItem;
import org.springframework.stereotype.Component;

@Component
public class InventoryItemMapper {

    public InventoryItemResponseDto toDto(InventoryItem item) {
        if (item == null) return null;

        InventoryItemResponseDto dto = new InventoryItemResponseDto();
        dto.setId(item.getId());
        dto.setCode(item.getCode());
        dto.setName(item.getName());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setQuantity(item.getQuantityInStock());

        return dto;
    }
}
