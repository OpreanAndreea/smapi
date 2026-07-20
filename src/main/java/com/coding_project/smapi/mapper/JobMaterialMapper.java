package com.coding_project.smapi.mapper;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.coding_project.smapi.dto.response.JobMaterialResponseDto;
import com.coding_project.smapi.entity.JobMaterial;

@Component
public class JobMaterialMapper {

    public JobMaterialResponseDto toDto(JobMaterial jobMaterial) {
        if (jobMaterial == null) return null;

        JobMaterialResponseDto dto = new JobMaterialResponseDto();
        dto.setId(jobMaterial.getId());
        dto.setJobId(jobMaterial.getJob().getId());
        dto.setInventoryItemCode(jobMaterial.getInventoryItem().getCode());
        dto.setPricePerItem(jobMaterial.getPriceAtAllocation());
        dto.setQuantity(jobMaterial.getQuantityUsed());
        dto.setTotalPrice(jobMaterial.getPriceAtAllocation().multiply(BigDecimal.valueOf(jobMaterial.getQuantityUsed())));
        return dto;
    }
}
