package com.ecommerceplatform.repository;

import com.ecommerceplatform.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
