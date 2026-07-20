package com.coding_project.smapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobMaterialRequestDto {
    private String inventoryItemCode;
    private int quantity;
}
