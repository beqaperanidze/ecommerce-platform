package com.ecommerceplatform.service;

import com.ecommerceplatform.dto.InventoryDto;

import java.util.List;

public interface InventoryService {
    InventoryDto createInventory(InventoryDto inventoryDto);

    InventoryDto getProductInventoryLevel(Long productId);

    InventoryDto updateProductInventoryLevel(Long productId, Integer newQuantity);

    InventoryDto adjustProductInventory(Long productId, Integer quantityChange, String reason);

    List<InventoryDto> getProductsBelowLowStockThreshold();
}