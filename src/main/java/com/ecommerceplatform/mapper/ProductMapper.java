package com.ecommerceplatform.mapper;

import com.ecommerceplatform.dto.ProductDto;
import com.ecommerceplatform.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

}