package com.ecommerceplatform.service.impl;

import com.ecommerceplatform.dto.InventoryDto;
import com.ecommerceplatform.mapper.InventoryMapper;
import com.ecommerceplatform.model.Product;
import com.ecommerceplatform.repository.InventoryRepository;
import com.ecommerceplatform.repository.ProductRepository;
import com.ecommerceplatform.service.InventoryService;

import java.util.List;

public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final InventoryMapper inventoryMapper;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductRepository productRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto) {
        return null;
    }

    @Override
    public InventoryDto getProductInventoryLevel(Long productId) {
        return null;
    }

    @Override
    public InventoryDto updateProductInventoryLevel(Long productId, Integer newQuantity) {
        return null;
    }

    @Override
    public InventoryDto adjustProductInventory(Long productId, Integer quantityChange, String reason) {
        return null;
    }

    @Override
    public List<InventoryDto> getProductsBelowLowStockThreshold() {
        return List.of();
    }
}
