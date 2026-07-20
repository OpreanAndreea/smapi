package com.coding_project.smapi.service;

import com.coding_project.smapi.dto.request.InventoryItemRequestDto;
import com.coding_project.smapi.dto.response.InventoryItemResponseDto;
import com.coding_project.smapi.entity.InventoryItem;
import com.coding_project.smapi.mapper.InventoryItemMapper;
import com.coding_project.smapi.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryItemMapper inventoryItemMapper;

    public List<InventoryItemResponseDto> getAllItems() {
        return inventoryRepository.findAll().stream()
                .map(inventoryItemMapper::toDto)
                .toList();
    }

    @Transactional
    public List<InventoryItemResponseDto> restockItems(List<InventoryItemRequestDto> restockItems) {
        List<String> codes = restockItems.stream()
                .map(InventoryItemRequestDto::getCode)
                .toList();

        List<InventoryItem> existingItems = inventoryRepository.findByCodeIn(codes);

        Map<String, InventoryItem> existingItemsMap = existingItems.stream()
                .collect(Collectors.toMap(InventoryItem::getCode, item -> item));

        List<InventoryItem> itemsToSave = new ArrayList<>();

        for (InventoryItemRequestDto request : restockItems) {
            InventoryItem item = existingItemsMap.get(request.getCode());

            if (item != null) {
                item.setUnitPrice(request.getPrice());
                item.setQuantityInStock(item.getQuantityInStock() + request.getQuantity());
                itemsToSave.add(item);
            } else {
                InventoryItem newItem = new InventoryItem();
                newItem.setCode(request.getCode());
                newItem.setName(request.getName());
                newItem.setUnitPrice(request.getPrice());
                newItem.setQuantityInStock(request.getQuantity());
                itemsToSave.add(newItem);
            }
        }

        List<InventoryItem> savedItems = inventoryRepository.saveAll(itemsToSave);

        return savedItems.stream()
                .map(inventoryItemMapper::toDto)
                .toList();
    }
}
