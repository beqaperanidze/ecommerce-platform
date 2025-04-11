package com.ecommerceplatform.controller;

import com.ecommerceplatform.dto.InventoryDto;
import com.ecommerceplatform.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<InventoryDto> createInventory(@RequestBody InventoryDto inventoryDto) {
        InventoryDto createdInventory = inventoryService.createInventory(inventoryDto);
        return ResponseEntity.created(URI.create("/inventory/" + createdInventory.getInventoryId())).body(createdInventory);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryDto> getProductInventoryLevel(@PathVariable Long productId) {
        InventoryDto inventoryLevel = inventoryService.getProductInventoryLevel(productId);
        return ResponseEntity.ok(inventoryLevel);
    }

    @PutMapping("/product/{productId}/location/{location}")
    public ResponseEntity<InventoryDto> updateProductInventoryLevel(
            @PathVariable Long productId,
            @PathVariable String location,
            @RequestParam Integer newQuantity) {
        InventoryDto updatedInventory = inventoryService.updateProductInventoryLevel(productId, location, newQuantity);
        return ResponseEntity.ok(updatedInventory);
    }

    @PostMapping("/product/{productId}/location/{location}/adjust")
    public ResponseEntity<InventoryDto> adjustProductInventory(
            @PathVariable Long productId,
            @PathVariable String location,
            @RequestParam Integer quantityChange) {
        InventoryDto adjustedInventory = inventoryService.adjustProductInventory(productId, location, quantityChange);
        return ResponseEntity.ok(adjustedInventory);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryDto>> getProductsBelowLowStockThreshold() {
        List<InventoryDto> lowStockProducts = inventoryService.getProductsBelowLowStockThreshold();
        return ResponseEntity.ok(lowStockProducts);
    }
}