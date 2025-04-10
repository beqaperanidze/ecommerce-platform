package com.ecommerceplatform.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryDto {
    private Long inventoryId;
    private Long productId;
    private Integer quantity;
    private String location;
}