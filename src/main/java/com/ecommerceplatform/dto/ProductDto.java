package com.ecommerceplatform.dto;

import com.ecommerceplatform.model.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private String name;
    private String description;
    private String sku;
    private Category category;
    private Map<String, String> attributes;
    private BigDecimal price;
    private String imageUrl;
}