package com.ecommerceplatform.repository;

import com.ecommerceplatform.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
