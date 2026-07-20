package com.coding_project.smapi.dto.response;

import java.math.BigDecimal;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemResponseDto {
    private Long id;
    private String code;
    private String name;
    private BigDecimal unitPrice;
    private int quantity;
}
