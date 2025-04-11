package com.ecommerceplatform.mapper;

import com.ecommerceplatform.dto.ProductDto;
import com.ecommerceplatform.model.Category;
import com.ecommerceplatform.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    default Category mapCategoryIdToCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        Category category = new Category();
        category.setCategoryId(categoryId);
        return category;
    }
}