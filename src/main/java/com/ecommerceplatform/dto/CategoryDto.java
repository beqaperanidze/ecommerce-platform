package com.ecommerceplatform.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Long categoryId;
    private String name;
    private String description;
}