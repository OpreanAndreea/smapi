package com.coding_project.smapi.service;

import com.coding_project.smapi.dto.request.JobMaterialRequestDto;
import com.coding_project.smapi.dto.response.JobMaterialResponseDto;
import com.coding_project.smapi.entity.InventoryItem;
import com.coding_project.smapi.entity.Job;
import com.coding_project.smapi.entity.JobMaterial;
import com.coding_project.smapi.mapper.JobMaterialMapper;
import com.coding_project.smapi.repository.InventoryRepository;
import com.coding_project.smapi.repository.JobMaterialRepository;
import com.coding_project.smapi.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobMaterialService {
    @Autowired
    private JobMaterialRepository jobMaterialRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private JobMaterialMapper jobMaterialMapper;

    public List<JobMaterialResponseDto> getMaterialsByJobId(Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found with ID: " + jobId);
        }

        return jobMaterialRepository.findByJobId(jobId).stream()
                .map(jobMaterialMapper::toDto)
                .toList();
    }

    public List<JobMaterialResponseDto> allocateMaterials(Long jobId, List<JobMaterialRequestDto> dtos) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<String> inventoryCodes = dtos.stream()
                .map(JobMaterialRequestDto::getInventoryItemCode)
                .toList();

        List<InventoryItem> inventoryItems = inventoryRepository.findByCodeIn(inventoryCodes);
        Map<String, InventoryItem> inventoryMap = inventoryItems.stream()
                .collect(Collectors.toMap(InventoryItem::getCode, item -> item));

        List<JobMaterial> allocationsToSave = new ArrayList<>();
        for (JobMaterialRequestDto request : dtos) {
            InventoryItem inventoryItem = inventoryMap.get(request.getInventoryItemCode());

            if (inventoryItem == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory item not found with code: " + request.getInventoryItemCode());
            }

            if (inventoryItem.getQuantityInStock() < request.getQuantity()) {
                throw new IllegalArgumentException(
                        "Insufficient stock for " + inventoryItem.getName() +
                        ". Requested: " + request.getQuantity() +
                        ", Available: " + inventoryItem.getQuantityInStock()
                );
            }
            inventoryItem.setQuantityInStock(inventoryItem.getQuantityInStock() - request.getQuantity());
            JobMaterial jobMaterial = new JobMaterial();
            jobMaterial.setJob(job);
            jobMaterial.setInventoryItem(inventoryItem);
            jobMaterial.setQuantityUsed(request.getQuantity());
            jobMaterial.setPriceAtAllocation(inventoryItem.getUnitPrice());
            allocationsToSave.add(jobMaterial);
        }
        inventoryRepository.saveAll(inventoryItems);
        List<JobMaterial> savedAllocations = jobMaterialRepository.saveAll(allocationsToSave);
        return savedAllocations.stream()
                .map(jobMaterialMapper::toDto)
                .toList();
    }

    public void removeMaterial(Long jobId, Long materialAllocationId) {
        JobMaterial allocation = jobMaterialRepository.findById(materialAllocationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Material allocation not found with ID: " + materialAllocationId));
        if (!allocation.getJob().getId().equals(jobId)) {
            throw new IllegalArgumentException("Allocation ID does not match the provided Job ID.");
        }
        InventoryItem inventoryItem = allocation.getInventoryItem();
        inventoryItem.setQuantityInStock(inventoryItem.getQuantityInStock() + allocation.getQuantityUsed());
        inventoryRepository.save(inventoryItem);
        jobMaterialRepository.delete(allocation);
    }
}
