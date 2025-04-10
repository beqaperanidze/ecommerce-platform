package com.ecommerceplatform.mapper;

import com.ecommerceplatform.dto.InventoryDto;
import com.ecommerceplatform.model.Inventory;
import com.ecommerceplatform.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(source = "product.productId", target = "productId")
    InventoryDto inventoryToInventoryDto(Inventory inventory);

    @Mapping(source = "productId", target = "product")
    Inventory inventoryDtoToInventory(InventoryDto inventoryDto);

    default Product mapProductIdToProduct(Long productId) {
        if (productId == null) {
            return null;
        }
        Product product = new Product();
        product.setProductId(productId);
        return product;
    }
}