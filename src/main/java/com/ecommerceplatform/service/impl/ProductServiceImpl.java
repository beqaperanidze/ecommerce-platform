package com.ecommerceplatform.service.impl;

import com.ecommerceplatform.dto.ProductDto;
import com.ecommerceplatform.exception.ProductNotFoundException;
import com.ecommerceplatform.mapper.ProductMapper;
import com.ecommerceplatform.model.Product;
import com.ecommerceplatform.repository.ProductRepository;
import com.ecommerceplatform.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.productToProductDto(savedProduct);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with Id: " + productId));
        return productMapper.productToProductDto(product);
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with Id: " + productId));

        Product updatedProduct = productMapper.productDtoToProduct(productDto);
        updatedProduct.setProductId(existingProduct.getProductId());
        updatedProduct.setCreatedAt(existingProduct.getCreatedAt());

        Product savedProduct = productRepository.save(updatedProduct);
        return productMapper.productToProductDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found with Id: " + productId);
        }
        productRepository.deleteById(productId);
    }
}