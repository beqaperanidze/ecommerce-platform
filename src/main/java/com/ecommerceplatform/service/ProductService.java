package com.ecommerceplatform.service;

import com.ecommerceplatform.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();

    ProductDto getProduct(Long productId);

    ProductDto updateProduct(Long productId, ProductDto productDto);

    void deleteProduct(Long productId);
}
