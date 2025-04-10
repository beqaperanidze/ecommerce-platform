package com.ecommerceplatform.repository;

import com.ecommerceplatform.model.Inventory;
import com.ecommerceplatform.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findAllByProduct(Product product);

    Optional<Inventory> findByProduct(Product product);

    List<Inventory> findAllByQuantityLessThan(int defaultLowStockThreshold);

    Optional<Object> findByProductAndLocation(Product product, String location);
}
