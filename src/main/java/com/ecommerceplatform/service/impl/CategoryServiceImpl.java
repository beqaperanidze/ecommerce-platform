package com.ecommerceplatform.service.impl;

import com.ecommerceplatform.dto.CategoryDto;
import com.ecommerceplatform.exception.CategoryNotFoundException;
import com.ecommerceplatform.model.Category;
import com.ecommerceplatform.repository.CategoryRepository;
import com.ecommerceplatform.service.CategoryService;
import com.ecommerceplatform.mapper.CategoryMapper;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.categoryDtoToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDto(savedCategory);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::categoryToCategoryDto).toList();
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new CategoryNotFoundException("Category not found with Id: " + categoryId));
        return categoryMapper.categoryToCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() ->
                new CategoryNotFoundException("Category not found with Id: " + categoryId));
        Category updatedCategory = categoryMapper.categoryDtoToCategory(categoryDto);
        updatedCategory.setCategoryId(existingCategory.getCategoryId());
        categoryRepository.save(updatedCategory);
        return categoryMapper.categoryToCategoryDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("Category not found with Id: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
}
