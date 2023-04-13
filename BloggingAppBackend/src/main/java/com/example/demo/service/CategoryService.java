package com.example.demo.service;

import java.util.List;

import com.example.demo.payload.CategoryDTO;

public interface CategoryService {
	
	public CategoryDTO createCategory(CategoryDTO categoryDto);

	public CategoryDTO updateCategory(CategoryDTO categoryDto, Integer categoryId);
	
	public CategoryDTO getCategory(Integer categoryId);

	public List<CategoryDTO> getAllCategories();

	public void deleteCategory(Integer categoryId);

}
