package com.ecommerceplatform.service;

import com.ecommerceplatform.dto.InventoryDto;

import java.util.List;

public interface InventoryService {
    InventoryDto createInventory(InventoryDto inventoryDto);

    InventoryDto getProductInventoryLevel(Long productId);

    InventoryDto updateProductInventoryLevel(Long productId, String location, Integer newQuantity);

    InventoryDto adjustProductInventory(Long productId, String location, Integer quantityChange);

    List<InventoryDto> getProductsBelowLowStockThreshold();
}