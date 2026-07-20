package com.coding_project.smapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobMaterialResponseDto {
    private Long id;
    private Long jobId;
    private String inventoryItemCode;
    private int quantity;
    private BigDecimal pricePerItem;
    private BigDecimal totalPrice;
}
