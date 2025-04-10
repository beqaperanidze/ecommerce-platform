package com.ecommerceplatform.service.impl;

import com.ecommerceplatform.dto.InventoryDto;
import com.ecommerceplatform.exception.ProductNotFoundException;
import com.ecommerceplatform.mapper.InventoryMapper;
import com.ecommerceplatform.model.Inventory;
import com.ecommerceplatform.model.Product;
import com.ecommerceplatform.repository.InventoryRepository;
import com.ecommerceplatform.repository.ProductRepository;
import com.ecommerceplatform.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final InventoryMapper inventoryMapper;

    private static final int DEFAULT_LOW_STOCK_THRESHOLD = 5;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductRepository productRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto) {
        Inventory inventory = inventoryMapper.inventoryDtoToInventory(inventoryDto);
        Long productId = inventoryDto.getProductId();

        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException("Product not found with ID: " + productId));

        if (inventoryDto.getLocation() != null) {
            inventoryRepository.findByProductAndLocation(product, inventoryDto.getLocation())
                    .ifPresent(existingInventory -> {
                        throw new IllegalArgumentException("Inventory already exists for product ID: " +
                                productId + " at location: " + inventoryDto.getLocation());
                    });
        }

        inventory.setProduct(product);
        inventory.setLastStockUpdate(LocalDateTime.now());
        Inventory savedInventory = inventoryRepository.save(inventory);

        return inventoryMapper.inventoryToInventoryDto(savedInventory);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryDto getProductInventoryLevel(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        List<Inventory> inventories = inventoryRepository.findAllByProduct(product);

        int totalQuantity = inventories.stream()
                .mapToInt(Inventory::getQuantity)
                .sum();

        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setProductId(productId);
        inventoryDto.setQuantity(totalQuantity);

        return inventoryDto;
    }

    @Override
    public InventoryDto updateProductInventoryLevel(Long productId, String location, Integer newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Inventory quantity cannot be negative");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        Inventory inventory = (Inventory) inventoryRepository.findByProductAndLocation(product, location)
                .orElseGet(() -> {
                    Inventory newInventory = new Inventory();
                    newInventory.setProduct(product);
                    newInventory.setLocation(location);
                    return newInventory;
                });

        inventory.setQuantity(newQuantity);
        inventory.setLastStockUpdate(LocalDateTime.now());
        Inventory savedInventory = inventoryRepository.save(inventory);

        return inventoryMapper.inventoryToInventoryDto(savedInventory);
    }

    public InventoryDto adjustProductInventory(Long productId, String location, Integer quantityChange) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        Inventory inventory = (Inventory) inventoryRepository.findByProductAndLocation(product, location)
                .orElseGet(() -> {
                    Inventory newInventory = new Inventory();
                    newInventory.setProduct(product);
                    newInventory.setQuantity(0);
                    newInventory.setLocation(location);
                    return newInventory;
                });

        int currentQuantity = inventory.getQuantity();
        int newQuantity = currentQuantity + quantityChange;

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Insufficient inventory at location " + location +
                    ". Current: " + currentQuantity + ", Requested change: " + quantityChange);
        }

        inventory.setQuantity(newQuantity);
        inventory.setLastStockUpdate(LocalDateTime.now());


        Inventory savedInventory = inventoryRepository.save(inventory);
        return inventoryMapper.inventoryToInventoryDto(savedInventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDto> getProductsBelowLowStockThreshold() {
        List<Product> allProducts = productRepository.findAll();

        return allProducts.stream()
                .filter(product -> {
                    List<Inventory> inventories = inventoryRepository.findAllByProduct(product);
                    int totalQuantity = inventories.stream()
                            .mapToInt(Inventory::getQuantity)
                            .sum();
                    return totalQuantity < DEFAULT_LOW_STOCK_THRESHOLD;
                })
                .map(product -> {
                    List<Inventory> inventories = inventoryRepository.findAllByProduct(product);
                    int totalQuantity = inventories.stream()
                            .mapToInt(Inventory::getQuantity)
                            .sum();

                    InventoryDto dto = new InventoryDto();
                    dto.setProductId(product.getProductId());
                    dto.setQuantity(totalQuantity);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}