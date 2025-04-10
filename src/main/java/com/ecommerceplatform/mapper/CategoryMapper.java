package com.ecommerceplatform.mapper;

import com.ecommerceplatform.dto.CategoryDto;
import com.ecommerceplatform.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto categoryToCategoryDto(Category category);

    Category categoryDtoToCategory(CategoryDto categoryDto);
}