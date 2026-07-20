package com.coding_project.smapi.dto.request;
        
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemRequestDto {
    private String code;
    private String name;
    private BigDecimal price;
    private int quantity;
}